package com.api.service.Imp;

import com.api.dto.request.AddToCartRequest;
import com.api.entity.*;
import com.api.repository.CartDetailRepository;
import com.api.repository.CartRepository;
import com.api.repository.FoodRepository;
import com.api.repository.UserRepository;
import com.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class CartServiceImp implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addToCart(Long userId, AddToCartRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        boolean cartExists  = cartRepository.existsByUserId(userId);

        Cart cart;
        if (cartExists == false) {
            cart = new Cart();
            cart.setUser(user);

            // Lưu món ăn đầu tiên
            Food food = foodRepository.findById(request.getFoodId()).orElseThrow(() -> new RuntimeException("Food not found"));
            CartDetail cartDetail = CartDetail.builder()
                    .cart(cart)
                    .food(food)
                    .quantity(request.getQuantity())
                    .note(request.getNote())
                    .build();
            cart.setCartDetails(List.of(cartDetail));
            cartRepository.save(cart);
            return;
        } else {
            cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        }        // Kiểm tra nếu món ăn đã có trong giỏ hàng, chỉ cần cập nhật số lượng
        CartDetail existingCartDetail = cartDetailRepository.findByCartIdAndFoodId(cart.getId(), request.getFoodId())
                .orElse(null);

        if (existingCartDetail != null) {
            existingCartDetail.setQuantity(existingCartDetail.getQuantity() + request.getQuantity());
            cartDetailRepository.save(existingCartDetail);
            return;
        }

        // Nếu món ăn chưa có trong giỏ hàng, thêm mới
        Food food = foodRepository.findById(request.getFoodId()).orElseThrow(() -> new RuntimeException("Food not found"));
        CartDetail cartDetail = CartDetail.builder()
                .cart(cart)
                .food(food)
                .quantity(request.getQuantity())
                .note(request.getNote())
                .build();

        cartDetailRepository.save(cartDetail);
    }

    @Override
    public void removeFromCart(Long cartId, Long foodId) {
        CartDetail cartDetail = cartDetailRepository.findByCartIdAndFoodId(cartId, foodId)
                .orElseThrow(() -> new RuntimeException("Cart detail not found"));

        cartDetailRepository.delete(cartDetail);
    }

    @Override
    public BigDecimal calculateTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartDetail cartDetail : cart.getCartDetails()) {
            FoodDetail currentFoodDetail = cartDetail.getFood().getFoodDetails().stream()
                    .filter(foodDetail -> foodDetail.getEndTime()==null)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Khong tim thay food detail gia tri hien tai"));
            BigDecimal itemTotal = currentFoodDetail.getPrice().multiply(BigDecimal.valueOf(cartDetail.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }

        return totalPrice;
    }
}
