package com.api.controller;

import com.api.dto.response.OrderResponse;
import com.api.entity.CartDetail;
import com.api.entity.Order;
import com.api.repository.OrderRepository;
import com.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestParam Long userId) {
        OrderResponse order = orderService.createOrder(userId);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<List<Order>> getListOrders(@PathVariable Long userId) {
        List<Order> order = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/{orderId}/cart-details")
    public ResponseEntity<List<CartDetail>> getListCartDetails(@PathVariable Long orderId) {
        List<CartDetail> cartDetails = orderService.getCartDetailsByOrder(orderId, "PENDING");
        return ResponseEntity.ok(cartDetails);
    }
}
