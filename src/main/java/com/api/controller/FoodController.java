package com.api.controller;

import com.api.dto.request.AddFoodRequest;
import com.api.dto.request.AdjustFoodPriceRequest;
import com.api.dto.response.ApiResponse;
import com.api.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodController {
    private final FoodService foodService;

    @PostMapping()
    public ApiResponse<Long> addNewFood(@RequestBody AddFoodRequest newFood) {
        return ApiResponse.<Long>builder()
                .code(200)
                .message("Success")
                .data(foodService.addFood(newFood))
                .build();
    }

    @PostMapping("/adjust-price")
    public ApiResponse<Long> adjustFoodPrice(@RequestBody AdjustFoodPriceRequest request) {
        return ApiResponse.<Long>builder()
                .code(200)
                .message("Success")
                .data(foodService.adjustFoodPrice(request))
                .build();
    }

}
