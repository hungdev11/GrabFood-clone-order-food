package com.api.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private String province;
    private String district;
    private String ward;
}
