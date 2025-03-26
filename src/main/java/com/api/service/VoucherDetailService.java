package com.api.service;

import com.api.dto.request.AddVoucherDetailRequest;
import com.api.dto.response.VoucherDetailResponse;

public interface VoucherDetailService {
    VoucherDetailResponse addVoucherDetails(AddVoucherDetailRequest request);
}
