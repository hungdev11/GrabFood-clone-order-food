package com.api.dto.request;

import com.api.model.FoodType;
import com.api.model.Restaurant;
import com.api.utils.FoodKind;
import com.api.utils.FoodStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddFoodRequest {
    private String name;
    private String image;
    private String description;
    private FoodKind kind;
    private String type;
    private BigDecimal price;
    private long restaurant_id;
}
