package com.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_voucher")
public class OrderVoucher extends BaseEntity {
    @Column(nullable = false)
    private LocalDateTime timeApplied;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private VoucherDetail voucherDetail;
}
