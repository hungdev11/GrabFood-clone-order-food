package com.api.controller;

import com.api.dto.request.AddFoodRequest;
import com.api.dto.request.AdjustFoodPriceRequest;
import com.api.dto.response.ApiResponse;
import com.api.dto.response.GetFoodResponse;
import com.api.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodController {
    private final FoodService foodService;

    @PostMapping()
    public ApiResponse<Long> addNewFood(@RequestBody AddFoodRequest newFood) {
        return ApiResponse.<Long>builder()
                .code(202)
                .message("Success")
                .data(foodService.addFood(newFood))
                .build();
    }

    @PostMapping("/adjust-price")
    public ApiResponse<Long> adjustFoodPrice(@RequestBody AdjustFoodPriceRequest request) {
        return ApiResponse.<Long>builder()
                .code(202)
                .message("Success")
                .data(foodService.adjustFoodPrice(request))
                .build();
    }

    @GetMapping("/{foodId}")
    public ApiResponse<GetFoodResponse> getFood(@PathVariable long foodId, @RequestParam boolean isForCustomer) {
        return ApiResponse.<GetFoodResponse>builder()
                .code(200)
                .message("Success")
                .data(foodService.getFood(foodId, isForCustomer))
                .build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ApiResponse<?> getFoodsOfRestaurant(
            @PathVariable long restaurantId,
            @RequestParam boolean isForCustomer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ApiResponse.builder()
                .data(foodService.getFoodsOfRestaurant(restaurantId, isForCustomer, page, pageSize))
                .message("Success")
                .code(200)
                .build();
    }

}
