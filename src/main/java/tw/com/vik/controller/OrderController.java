package tw.com.vik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tw.com.vik.dto.CreateOrderRequest;
import tw.com.vik.model.Order;
import tw.com.vik.service.OrderService;

@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(
            @PathVariable Integer userId,
            @RequestBody @Valid CreateOrderRequest createOrderRequest)
    {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);
        
        Order order = orderService.getOrderById(orderId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
