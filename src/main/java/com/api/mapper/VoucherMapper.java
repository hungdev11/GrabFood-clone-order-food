package com.api.mapper;

import com.api.dto.request.VoucherRequest;
import com.api.dto.response.VoucherResponse;
import com.api.model.Voucher;

public interface VoucherMapper {
    Voucher toVoucher(VoucherRequest request);

    VoucherResponse toVoucherResponse(Voucher voucher);
}
