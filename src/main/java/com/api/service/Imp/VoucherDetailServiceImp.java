package com.api.service.Imp;

import com.api.dto.request.AddVoucherDetailRequest;
import com.api.entity.Food;
import com.api.entity.Voucher;
import com.api.entity.VoucherDetail;
import com.api.repository.FoodRepository;
import com.api.repository.VoucherDetailRepository;
import com.api.repository.VoucherRepository;
import com.api.service.VoucherDetailService;
import com.api.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoucherDetailServiceImp implements VoucherDetailService {
    private final FoodRepository foodRepository;
    private final VoucherService voucherService;
    private final VoucherDetailRepository voucherDetailRepository;
    @Override
    public long addVoucherDetails(AddVoucherDetailRequest request) {
        VoucherDetail voucherDetail = VoucherDetail.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        Food food = foodRepository.findById(request.getFood_id()).orElse(null);
        Voucher voucher = voucherService.getVoucherbyId(request.getVoucher_id());
        voucherDetail.setVoucher(voucher);
        voucherDetail.setFood(food);
        voucherDetailRepository.save(voucherDetail);
        return 0;
    }
}
