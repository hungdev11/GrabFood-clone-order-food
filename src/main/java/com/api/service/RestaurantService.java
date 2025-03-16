package com.api.service;

import com.api.dto.request.AddRestaurantRequest;

public interface RestaurantService {
    long addRestaurant(AddRestaurantRequest request);
}
