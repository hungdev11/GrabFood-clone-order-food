package com.api.service.Imp;

import com.api.dto.request.AddFoodRequest;
import com.api.model.Food;
import com.api.model.FoodType;
import com.api.model.Restaurant;
import com.api.repository.FoodRepository;
import com.api.service.FoodService;
import com.api.service.FoodTypeService;
import com.api.service.RestaurantService;
import com.api.utils.FoodStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodServiceImp implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodTypeService foodTypeService;
    private final RestaurantService restaurantService;

    @Override
    @Transactional
    public long addFood(AddFoodRequest request) {
//        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurant_id());
//
//        FoodType foodType = foodTypeService.getFoodTypeByName(request.getType());
//
//        Food newFood = Food.builder()
//                .name(request.getName())
//                .description(request.getDescription())
//                .kind(request.getKind())
//                .image(request.getImage())
//                .type(foodType)
//                .status(FoodStatus.ACTIVE)
//                .restaurant(restaurant)
//                .build();
//        foodRepository.save(newFood);
//        foodType.getFoods().add(newFood);
        return 0;
    }
}
