package com.api.service;

import com.api.dto.request.VoucherRequest;
import com.api.dto.response.VoucherResponse;
import com.api.model.Voucher;

public interface VoucherService {
    VoucherResponse addVoucher(VoucherRequest request);

    Voucher getVoucherbyId(long id);
}
