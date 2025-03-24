package com.api.controller;

import com.api.dto.request.AddToCartRequest;
import com.api.entity.CartDetail;
import com.api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestParam Long userId, @RequestBody AddToCartRequest request) {
        cartService.addToCart(userId, request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{cartId}/remove/{foodId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId, @PathVariable Long foodId) {
        cartService.removeFromCart(cartId, foodId);
        return ResponseEntity.noContent().build();
    }
}
