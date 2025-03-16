package com.api.service;

import com.api.dto.request.AddFoodTypeRequest;
import com.api.model.FoodType;

import java.util.Optional;

public interface FoodTypeService {
    long addNewFoodType(String name);
    FoodType getFoodTypeByName(String name);
}
