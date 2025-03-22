package com.api.service.Imp;

import com.api.dto.request.AddToCartRequest;
import com.api.entity.Cart;
import com.api.entity.CartDetail;
import com.api.entity.Food;
import com.api.entity.User;
import com.api.repository.CartDetailRepository;
import com.api.repository.CartRepository;
import com.api.repository.FoodRepository;
import com.api.repository.UserRepository;
import com.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CartDetail addToCart(Long userId, AddToCartRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra giỏ hàng, nếu chưa có thì tạo mới
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            Cart savedCart = cartRepository.save(newCart);

            // Lưu món ăn đầu tiên vào giỏ hàng mới
            Food food = foodRepository.findById(request.getFoodId()).orElseThrow(() -> new RuntimeException("Food not found"));
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(savedCart);
            cartDetail.setFood(food);
            cartDetail.setQuantity(request.getQuantity());
            cartDetail.setNote(request.getNote());
            cartDetailRepository.save(cartDetail);

            return savedCart; // Trả về giỏ hàng đã lưu
        });

        // Kiểm tra nếu món ăn đã có trong giỏ hàng, chỉ cần cập nhật số lượng
        CartDetail existingCartDetail = cartDetailRepository.findByCartIdAndFoodId(cart.getId(), request.getFoodId())
                .orElse(null);

        if (existingCartDetail != null) {
            existingCartDetail.setQuantity(existingCartDetail.getQuantity() + request.getQuantity());
            return cartDetailRepository.save(existingCartDetail);
        }

        // Nếu món ăn chưa có trong giỏ hàng, thêm mới
        Food food = foodRepository.findById(request.getFoodId()).orElseThrow(() -> new RuntimeException("Food not found"));
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setFood(food);
        cartDetail.setQuantity(request.getQuantity());
        cartDetail.setNote(request.getNote());

        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void removeFromCart(Long cartId, Long foodId) {
        CartDetail cartDetail = cartDetailRepository.findByCartIdAndFoodId(cartId, foodId)
                .orElseThrow(() -> new RuntimeException("Cart detail not found"));

        cartDetailRepository.delete(cartDetail);
    }
}
