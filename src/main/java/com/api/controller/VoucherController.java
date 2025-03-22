package com.api.controller;

import com.api.dto.request.VoucherRequest;
import com.api.dto.response.ApiResponse;
import com.api.dto.response.VoucherResponse;
import com.api.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping
    public ApiResponse<VoucherResponse> addNewVoucher(@Validated @RequestBody VoucherRequest request) {
        return ApiResponse.<VoucherResponse>builder()
                .code(200)
                .message("Success")
                .data(voucherService.addVoucher(request))
                .build();
    }
}
