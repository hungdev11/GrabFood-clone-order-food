package com.api.service.Imp;

import com.api.dto.request.AddFoodRequest;
import com.api.dto.request.AdjustFoodPriceRequest;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.entity.Food;
import com.api.entity.FoodDetail;
import com.api.entity.FoodType;
import com.api.entity.Restaurant;
import com.api.repository.FoodRepository;
import com.api.service.FoodService;
import com.api.service.FoodTypeService;
import com.api.service.RestaurantService;
import com.api.utils.FoodStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

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
        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurant_id());
        FoodType foodType = foodTypeService.getFoodTypeByName(request.getType());

        if (foodRepository.existsByRestaurantAndNameAndTypeAndKind(
                restaurant, request.getName(), foodType, request.getKind())) {
            log.info("Food already exists");
            throw new AppException(ErrorCode.FOOD_OF_RETAURANT_EXISTED);
        }

        Food newFood = Food.builder()
                .name(request.getName())
                .description(request.getDescription())
                .kind(request.getKind())
                .image(request.getImage())
                .type(foodType)
                .status(FoodStatus.ACTIVE)
                .restaurant(restaurant)
                .build();

        newFood = foodRepository.save(newFood);

        FoodDetail foodDetail = FoodDetail.builder()
                .price(request.getPrice())
                .startTime(LocalDateTime.now())
                .endTime(null)
                .food(newFood)
                .build();
        newFood.getFoodDetails().add(foodDetail);

        foodType.getFoods().add(newFood);
        restaurant.getFoods().add(newFood);

        return newFood.getId();
    }

    @Override
    @Transactional
    public long adjustFoodPrice(AdjustFoodPriceRequest request) {
        if (request.getOldPrice().equals(request.getNewPrice())) {
            log.info("Prices are the same");
            throw new AppException(ErrorCode.FOOD_PRICE_REDUNDANT);
        }

        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurant_id());

        Food food = foodRepository.findById(request.getFood_id()).orElseThrow(() -> {
            log.info("Food not found");
            return new AppException(ErrorCode.FOOD_NOT_FOUND);
        });

        if (!food.getRestaurant().equals(restaurant)) {
            log.info("Food id {} not belong to restaurant {}", request.getFood_id(), request.getRestaurant_id());
            throw new AppException(ErrorCode.FOOD_RESTAURANT_NOT_FOUND);
        }

        FoodDetail newestDetail = food.getFoodDetails().stream()
                .sorted(Comparator.comparing(FoodDetail::getStartTime).reversed())
                .findFirst()
                .orElse(null);

        if (newestDetail != null) {
            if (newestDetail.getPrice().compareTo(request.getOldPrice()) != 0) {
                log.info("Previous price conflict {} vs {}", newestDetail.getPrice(), request.getOldPrice());
                throw new AppException(ErrorCode.FOOD_DETAIL_CONFLICT_PRICE);
            }
            newestDetail.setEndTime(LocalDateTime.now());
        }

        FoodDetail newFoodDetail = FoodDetail.builder()
                .price(request.getNewPrice())
                .startTime(LocalDateTime.now())
                .endTime(null)
                .food(food)
                .build();

        food.getFoodDetails().add(newFoodDetail);

        return foodRepository.save(food).getId();
    }


}
