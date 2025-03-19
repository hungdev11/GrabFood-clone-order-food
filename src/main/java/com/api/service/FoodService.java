package com.api.service;

import com.api.dto.request.AddFoodRequest;
import com.api.dto.request.AdjustFoodPriceRequest;
import com.api.dto.response.GetFoodResponse;
import com.api.utils.FoodStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FoodService {
    long addFood(AddFoodRequest food);

    long adjustFoodPrice(AdjustFoodPriceRequest adjustFoodPriceRequest);

    BigDecimal getCurrentPrice(long foodId);

    BigDecimal getFoodPriceIn(long foodId, LocalDateTime time);

    public GetFoodResponse getFood(long foodId, boolean isForCustomer);
}
