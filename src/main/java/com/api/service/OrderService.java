package com.api.service;

import com.api.dto.response.OrderResponse;
import com.api.entity.CartDetail;
import com.api.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(Long userId);
    List<CartDetail> getCartDetailsByOrder(Long orderId, String status);
    List<Order> getOrdersByUser(Long userId);
}
