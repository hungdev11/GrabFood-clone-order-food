package com.api.controller;

import com.api.dto.request.AddRestaurantRequest;
import com.api.dto.response.ApiResponse;
import com.api.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ApiResponse<Long> addNewRestaurant(@RequestBody AddRestaurantRequest newRestaurant) {
        try {
            return ApiResponse.<Long>builder()
                    .code(200)
                    .message("Success")
                    .data(restaurantService.addRestaurant(newRestaurant))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Long>builder()
                    .code(404)
                    .message("Failure")
                    .build();
        }
    }
}
