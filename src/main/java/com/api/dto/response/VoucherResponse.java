package com.api.dto.response;

import com.api.utils.VoucherStatus;
import com.api.utils.VoucherType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherResponse {
    private long id;
    private String description;

    private int quantity;

    private BigDecimal minRequire;

    private VoucherType type;

    private VoucherStatus status;

    private String restaurant_name;
}
