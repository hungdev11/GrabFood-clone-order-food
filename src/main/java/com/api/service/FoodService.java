package com.api.service;

import com.api.dto.request.AddFoodRequest;
import com.api.model.Food;

public interface FoodService {
    long addFood(AddFoodRequest food);
}
