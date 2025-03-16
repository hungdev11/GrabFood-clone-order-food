package com.api.model;

import com.api.utility.VoucherStatus;
import com.api.utility.VoucherType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class Voucher extends BaseEntity{
    private String description;
    private int quantity;
    private BigDecimal minRequire;
    private VoucherType type;
    private VoucherStatus status;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherDetail> voucherDetails = new ArrayList<>();
}
