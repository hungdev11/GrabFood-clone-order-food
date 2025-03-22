package com.api.service;

import com.api.dto.request.AddToCartRequest;
import com.api.entity.CartDetail;

public interface CartService {
    CartDetail addToCart(Long userId, AddToCartRequest addToCartRequest);
    void removeFromCart(Long cartId, Long foodId);
}
