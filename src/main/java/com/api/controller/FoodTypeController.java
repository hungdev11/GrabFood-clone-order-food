package com.api.controller;

import com.api.dto.request.AddFoodTypeRequest;
import com.api.dto.request.AddRestaurantRequest;
import com.api.dto.response.ApiResponse;
import com.api.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/food-type")
public class FoodTypeController {
    private final FoodTypeService foodTypeService;

    @PostMapping
    public ApiResponse<Long> addNewFoodType(@RequestParam String name) {
        try {
            return ApiResponse.<Long>builder()
                    .code(200)
                    .message("Success")
                    .data(foodTypeService.addNewFoodType(name))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Long>builder()
                    .code(400)
                    .message("Failure")
                    .build();
        }
    }
}
