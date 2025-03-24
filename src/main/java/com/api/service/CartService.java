package com.api.service;

import com.api.dto.request.AddToCartRequest;
import com.api.entity.CartDetail;

import java.math.BigDecimal;

public interface CartService {
    void addToCart(Long userId, AddToCartRequest addToCartRequest);
    void removeFromCart(Long cartId, Long foodId);
    BigDecimal calculateTotalPrice(Long cartId);
}
