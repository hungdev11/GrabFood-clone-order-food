package com.api.repository;

import com.api.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<CartDetail> findByCartIdAndFoodId(Long cartId, Long foodId);

}
