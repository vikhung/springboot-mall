package tw.com.vik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import tw.com.vik.constant.ProductCategory;
import tw.com.vik.dto.CreateOrderRequest;
import tw.com.vik.dto.OrderQueryParams;
import tw.com.vik.dto.ProductQueryParams;
import tw.com.vik.model.Order;
import tw.com.vik.model.Product;
import tw.com.vik.service.OrderService;
import tw.com.vik.util.Page;

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
        //新增訂單
        Integer orderId = orderService.createOrder(userId, createOrderRequest);
        
        //查詢訂單
        Order order = orderService.getOrderById(orderId);
        
        //回應訂單資訊
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset)
    {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);
        
        //取得訂單資訊
        List<Order> orderList = orderService.getOrders(orderQueryParams);
        
        //取得訂單總量
        Integer total = orderService.countOrder(orderQueryParams);
        
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);
        
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
