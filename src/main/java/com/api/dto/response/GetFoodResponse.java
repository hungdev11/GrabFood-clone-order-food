package com.api.dto.response;

import com.api.utils.FoodStatus;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFoodResponse {
    private String name;
    private String image;
    private String description;
    private FoodStatus status;
    private BigDecimal price;
    private BigDecimal rating;
}
