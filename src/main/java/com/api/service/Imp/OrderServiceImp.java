package com.api.service.Imp;

import com.api.dto.response.OrderResponse;
import com.api.entity.Cart;
import com.api.entity.CartDetail;
import com.api.entity.Order;
import com.api.entity.User;
import com.api.mapper.OrderMapper;
import com.api.repository.CartDetailRepository;
import com.api.repository.CartRepository;
import com.api.repository.OrderRepository;
import com.api.repository.UserRepository;
import com.api.service.CartService;
import com.api.service.OrderService;
import com.api.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public OrderResponse createOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
// Kiểm tra xem đã có mặt hàng nào chưa
        if (cart.getCartDetails().isEmpty()) {
            throw new RuntimeException("Cannot create order: Cart is empty");
        }

        Order order =  Order.builder()
                        .user(user)
                        .totalPrice(cartService.calculateTotalPrice(cart.getId()))
                        .address("Default Address")
                        .status(OrderStatus.PENDING)
                        .shippingFee(BigDecimal.ZERO)
                        .build();
        orderRepository.save(order);
        List<CartDetail> updatedCartDetails = cart.getCartDetails().stream().map(cartDetail -> {
            cartDetail.setOrder(order);
            cartDetail.setCart(null);
            return cartDetailRepository.save(cartDetail);
        }).toList();

        order.setCartDetails(updatedCartDetails);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<CartDetail> getCartDetailsByOrder(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getCartDetails();
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getOrders();
    }
}
