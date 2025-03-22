package com.api.service.Imp;

import com.api.dto.request.VoucherRequest;
import com.api.entity.Restaurant;
import com.api.entity.Voucher;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.mapper.Imp.VoucherMapperImp;
import com.api.mapper.VoucherMapper;


import com.api.repository.VoucherRepository;
import com.api.service.RestaurantService;
import com.api.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherServiceImp implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final RestaurantService restaurantService;

    @Override
    public long addVoucher(VoucherRequest request) {
        VoucherMapperImp voucherMapper = new VoucherMapperImp();
        Voucher voucher = voucherMapper.toVoucher(request);
        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurant_id());
        voucher.setRestaurant(restaurant);
        voucherRepository.save(voucher);
        return voucher.getId();
    }

    @Override
    public Voucher getVoucherbyId(long id) {
        return voucherRepository.findById(id).orElseThrow(() -> {
            log.info("Voucher not found");
            return new AppException(ErrorCode.VOUCHER_NOT_FOUND);
        });
    }
}
