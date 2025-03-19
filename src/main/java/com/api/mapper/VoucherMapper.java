package com.api.mapper;

import com.api.dto.request.VoucherRequest;
import com.api.model.Voucher;

public interface VoucherMapper {
    Voucher toVoucher(VoucherRequest request);
}
