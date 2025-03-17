package com.api.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustFoodPriceRequest {
    private long restaurant_id;
    private long food_id;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
}
