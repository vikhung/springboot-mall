package tw.com.vik.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import tw.com.vik.dao.OrderDao;
import tw.com.vik.dao.ProductDao;
import tw.com.vik.dto.BuyItem;
import tw.com.vik.dto.CreateOrderRequest;
import tw.com.vik.model.OrderItem;
import tw.com.vik.model.Product;
import tw.com.vik.model.Order;
import tw.com.vik.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private OrderDao orderDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, @Valid CreateOrderRequest createOrderRequest)
    {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        
        for(BuyItem buyItem : createOrderRequest.getBuyItemList())
        {
            Product product = productDao.getProductById(buyItem.getProductId());
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }
        
        
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        
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
}
