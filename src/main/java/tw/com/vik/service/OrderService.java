package tw.com.vik.service;

import jakarta.validation.Valid;
import tw.com.vik.dto.CreateOrderRequest;
import tw.com.vik.model.Order;

public interface OrderService
{

    Integer createOrder(Integer userId, @Valid CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

}
