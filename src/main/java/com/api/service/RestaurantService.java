package com.api.service;

import com.api.dto.request.AddRestaurantRequest;
import com.api.dto.response.RestaurantResponse;
import com.api.entity.Restaurant;

public interface RestaurantService {
    long addRestaurant(AddRestaurantRequest request);
    Restaurant getRestaurant(long id);
    RestaurantResponse getRestaurantResponse(long id);

}
