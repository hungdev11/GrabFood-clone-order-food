package com.api.service;

import com.api.dto.request.VoucherRequest;
import com.api.model.Voucher;

public interface VoucherService {
    long addVoucher(VoucherRequest request);
    Voucher getVoucherbyId(long id);
}
