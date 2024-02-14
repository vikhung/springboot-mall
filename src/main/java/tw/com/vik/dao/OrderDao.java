package tw.com.vik.dao;

import java.util.List;

import tw.com.vik.dto.OrderQueryParams;
import tw.com.vik.model.Order;
import tw.com.vik.model.OrderItem;

public interface OrderDao
{

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    
    Order getOrderById(Integer orderId);
    
    Integer countOrder(OrderQueryParams orderQueryParams);
    
    Integer createOrder(Integer userId, int totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

}
