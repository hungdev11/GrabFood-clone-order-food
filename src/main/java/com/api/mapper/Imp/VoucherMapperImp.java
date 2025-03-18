package com.api.mapper.Imp;

import com.api.dto.request.VoucherRequest;
import com.api.mapper.VoucherMapper;
import com.api.entity.Voucher;
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
                .build();
        return voucher;
    }
}
