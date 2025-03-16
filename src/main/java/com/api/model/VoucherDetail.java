package com.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "voucher_detail")
public class VoucherDetail extends BaseEntity{
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @OneToMany(mappedBy = "voucherDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderVoucher> orderVoucherList = new ArrayList<>();
}
