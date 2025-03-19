package com.api.mapper.Imp;

import com.api.dto.request.VoucherRequest;
import com.api.dto.response.VoucherResponse;
import com.api.mapper.VoucherMapper;
import com.api.model.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class VoucherMapperImp implements VoucherMapper {
    @Override
    public Voucher toVoucher(VoucherRequest request) {
        Voucher voucher = Voucher.builder()
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .minRequire(request.getMinRequire())
                .type(request.getType())
                .status(request.getStatus())
                .value(request.getValue())
                .build();
        return voucher;
    }

    @Override
    public VoucherResponse toVoucherResponse(Voucher voucher) {
        VoucherResponse voucherResponse =
                VoucherResponse.builder()
                        .description(voucher.getDescription())
                        .id(voucher.getId())
                        .minRequire(voucher.getMinRequire())
                        .quantity(voucher.getQuantity())
                        .status(voucher.getStatus())
                        .type(voucher.getType())
                        .value(voucher.getValue())
                .build();
        return voucherResponse;
    }


}
