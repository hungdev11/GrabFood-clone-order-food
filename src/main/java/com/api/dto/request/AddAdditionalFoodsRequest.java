package com.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAdditionalFoodsRequest {
    private long restaurantId;
    private long foodId;
    private Set<Integer> additionalFoodIds;
}
