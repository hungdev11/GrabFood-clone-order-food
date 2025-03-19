package com.api.repository;

import com.api.entity.Food;
import com.api.entity.FoodType;
import com.api.entity.Restaurant;
import com.api.utils.FoodKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    boolean existsByRestaurantAndNameAndTypeAndKind(Restaurant restaurant, String name, FoodType type, FoodKind kind);
    Optional<Food> findByRestaurantAndNameAndTypeAndKind(Restaurant restaurant, String name, FoodType type, FoodKind kind);
}
