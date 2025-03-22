package com.api.service.Imp;

import com.api.dto.request.VoucherRequest;
import com.api.dto.response.VoucherResponse;
import com.api.entity.Restaurant;
import com.api.entity.Voucher;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.mapper.Imp.VoucherMapperImp;

import com.api.repository.VoucherRepository;
import com.api.service.RestaurantService;
import com.api.service.VoucherService;
import com.api.utils.VoucherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherServiceImp implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final RestaurantService restaurantService;

    @Override
    public VoucherResponse addVoucher(VoucherRequest request) {
        VoucherMapperImp voucherMapper = new VoucherMapperImp();
        Voucher voucher = voucherMapper.toVoucher(request);
        //Check voucher value
        log.info("Check voucher value of voucher {}", voucher.getId());
        checkVoucherValue(request.getType(), request.getValue());
        Restaurant restaurant = new Restaurant();
        if(request.getRestaurant_id() > 0) {
            restaurant = restaurantService.getRestaurant(request.getRestaurant_id());
        } else {
            restaurant = null;
        }
        voucher.setRestaurant(restaurant);
        voucherRepository.save(voucher);
        //
        VoucherResponse response = voucherMapper.toVoucherResponse(voucher);
        if(restaurant != null) {
            response.setRestaurant_name(restaurant.getName());
        }
        return response;
    }

    @Override
    public Voucher getVoucherbyId(long id) {
        return voucherRepository.findById(id).orElseThrow(() -> {
            log.info("Voucher not found");
            return new AppException(ErrorCode.VOUCHER_NOT_FOUND);
        });
    }

    public void checkVoucherValue( VoucherType type,BigDecimal value) {
        if(type.equals(VoucherType.PERCENTAGE)) {
            if (value.compareTo(BigDecimal.ZERO) <= 0 || value.compareTo(new BigDecimal("100")) > 0) {
                throw  new AppException(ErrorCode.VOUCHER_VALUE_CONFLICT);
            }
        }
    }
}
