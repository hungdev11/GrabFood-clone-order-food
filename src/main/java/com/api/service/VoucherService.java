package com.api.service;

import com.api.dto.request.VoucherRequest;

import com.api.entity.Voucher;
import com.api.dto.response.VoucherResponse;


public interface VoucherService {
    VoucherResponse addVoucher(VoucherRequest request);

    Voucher getVoucherbyId(long id);
}
