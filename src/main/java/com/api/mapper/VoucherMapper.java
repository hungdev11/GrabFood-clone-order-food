package com.api.mapper;

import com.api.dto.request.VoucherRequest;
import com.api.entity.Voucher;

public interface VoucherMapper {
    Voucher toVoucher(VoucherRequest request);
}
