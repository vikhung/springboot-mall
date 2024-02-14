package tw.com.vik.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import tw.com.vik.dao.OrderDao;
import tw.com.vik.dao.ProductDao;
import tw.com.vik.dao.UserDao;
import tw.com.vik.dto.BuyItem;
import tw.com.vik.dto.CreateOrderRequest;
import tw.com.vik.dto.OrderQueryParams;
import tw.com.vik.model.OrderItem;
import tw.com.vik.model.Product;
import tw.com.vik.model.User;
import tw.com.vik.model.Order;
import tw.com.vik.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService
{
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, @Valid CreateOrderRequest createOrderRequest)
    {
        //檢查使用者是否存在
        User user = userDao.getUserById(userId);
        if(user == null)
        {
            log.warn("該 userId: {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
        
        //整理OerderItem
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        
        for(BuyItem buyItem : createOrderRequest.getBuyItemList())
        {
            Product product = productDao.getProductById(buyItem.getProductId());
            
            
            //檢查商品是否存在且庫存量足夠
            if(product == null)
            {
                log.warn("商品:{} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if(product.getStock() < buyItem.getQuantity())
            {
                log.warn("商品:{} 庫存數量不足。庫存量:{}，購買量:{}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            
            
            //更新商品庫存
            productDao.updateStock(product.getProductId(), product.getStock()-buyItem.getQuantity());
            
            
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }
        
        //新增訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        
        //新增訂單明細
        orderDao.createOrderItems(orderId, orderItemList);
        
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId)
    {
        Order order = orderDao.getOrderById(orderId);
        
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        
        order.setOrderItemList(orderItemList);
        
        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams)
    {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);
        
        for(Order order : orderList)
        {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams)
    {
        return orderDao.countOrder(orderQueryParams);
    }
}
