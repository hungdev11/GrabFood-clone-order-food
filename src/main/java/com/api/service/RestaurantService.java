package com.api.service;

import com.api.dto.request.AddRestaurantRequest;
import com.api.model.Restaurant;

public interface RestaurantService {
    long addRestaurant(AddRestaurantRequest request);
    Restaurant getRestaurant(long id);
}
