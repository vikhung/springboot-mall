package tw.com.vik.dao;

import java.util.List;

import tw.com.vik.model.Order;
import tw.com.vik.model.OrderItem;

public interface OrderDao
{

    Integer createOrder(Integer userId, int totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
