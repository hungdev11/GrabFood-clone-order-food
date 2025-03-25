package com.api.repository;

import com.api.entity.VoucherDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherDetailRepository extends JpaRepository<VoucherDetail,Long> {
    boolean existsByVoucherId(Long id);
}
