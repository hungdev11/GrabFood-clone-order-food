package com.api.dto.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long foodId;
    private int quantity;
    private String note;
}
