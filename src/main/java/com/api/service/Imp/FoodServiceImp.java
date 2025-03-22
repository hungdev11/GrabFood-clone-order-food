package com.api.service.Imp;

import com.api.dto.request.AddFoodRequest;
import com.api.dto.request.AdjustFoodPriceRequest;
import com.api.dto.response.GetFoodResponse;
import com.api.dto.response.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        log.info("Adding new food");
        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurant_id());
        FoodType foodType = foodTypeService.getFoodTypeByName(request.getType());

        if (foodRepository.existsByRestaurantAndNameAndTypeAndKind(
                restaurant, request.getName(), foodType, request.getKind())) {
            log.error("Food already exists");
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
        log.info("adjust Food Price");
        if (request.getOldPrice().equals(request.getNewPrice())) {
            log.error("Prices are the same");
            throw new AppException(ErrorCode.FOOD_PRICE_REDUNDANT);
        }

        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurantId());

        Food food = getFoodById(request.getFoodId());

        if (!food.getRestaurant().equals(restaurant)) {
            log.error("Food id {} not belong to restaurant {}", request.getFoodId(), request.getRestaurantId());
            throw new AppException(ErrorCode.FOOD_RESTAURANT_NOT_FOUND);
        }

        FoodDetail newestDetail = food.getFoodDetails().stream()
                .max(Comparator.comparing(FoodDetail::getStartTime))
                .orElseThrow(() -> {
                    log.error("Food id {} not found any detail", request.getFoodId());
                    return new AppException(ErrorCode.FOOD_NOT_FOUND);
                });

        if (newestDetail != null) {
            if (newestDetail.getPrice().compareTo(request.getOldPrice()) != 0) {
                log.error("Previous price conflict {} vs {}", newestDetail.getPrice(), request.getOldPrice());
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

    @Override
    public BigDecimal getCurrentPrice(long foodId) {
        log.info("Current price of {}", foodId);
        Food food = getFoodById(foodId);
        BigDecimal price = BigDecimal.ZERO;
        for (FoodDetail foodDetail : food.getFoodDetails()) {
            if (foodDetail.getEndTime() == null) {
                price = foodDetail.getPrice();
                break;
            }
        }
        return price;
    }

    @Override
    public BigDecimal getFoodPriceIn(long foodId, LocalDateTime time) {
        log.info("Get food price of {} in {}", foodId, time);
        Food food = getFoodById(foodId);
        BigDecimal price = BigDecimal.ZERO;
        for (FoodDetail foodDetail : food.getFoodDetails()) {
            if (time.isAfter(foodDetail.getEndTime()) && time.isBefore(foodDetail.getStartTime())) {
                price = foodDetail.getPrice();
                break;
            }
        }
        return price;
    }

    @Override
    public GetFoodResponse getFood(long foodId, boolean isForCustomer) {
        log.info("Get food info {}", foodId);
        Food food = getFoodById(foodId);

        if (isForCustomer && food.getStatus() == FoodStatus.INACTIVE) {
            log.error("Food id {} not public", foodId);
            throw new AppException(ErrorCode.FOOD_NOT_PUBLIC_FOR_CUSTOMER);
        }

        return GetFoodResponse.builder()
                .name(food.getName())
                .image(food.getImage())
                .description(food.getDescription())
                .price(getCurrentPrice(food.getId()))
                .rating(BigDecimal.ZERO)
                .build();
    }

    @Override
    public PageResponse<List<GetFoodResponse>> getFoodsOfRestaurant(long restaurantId, boolean isForCustomer, int page, int pageSize) {
        log.info("Get foods of restaurant {}", restaurantId);
        log.info("{} foods in page {}", pageSize, page);

        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Food> foodPage;
        if (isForCustomer) {
            foodPage = foodRepository.findByRestaurantAndStatus(restaurant, FoodStatus.ACTIVE, pageable);
        } else {
            foodPage = foodRepository.findByRestaurant(restaurant, pageable);
        }

        List<GetFoodResponse> foodResponses = foodPage.getContent().stream()
                .map(food -> GetFoodResponse.builder()
                        .name(food.getName())
                        .image(food.getImage())
                        .description(food.getDescription())
                        .price(getCurrentPrice(food.getId()))
                        .rating(BigDecimal.ZERO)
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<List<GetFoodResponse>>builder()
                .page(page)
                .size(pageSize)
                .total(foodPage.getTotalElements())
                .items(foodResponses)
                .build();
    }

    private Food getFoodById(long id) {
        return foodRepository.findById(id).orElseThrow(() -> {
            log.error("Food not found");
            return new AppException(ErrorCode.FOOD_NOT_FOUND);
        });
    }

}
