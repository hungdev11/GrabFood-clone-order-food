package com.api.entity;

import com.api.utils.VoucherStatus;
import com.api.utils.VoucherType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Voucher extends BaseEntity {

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(nullable = false)
    private int quantity;

    private BigDecimal minRequire;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoucherType type;

    @Column(nullable = false, precision = 7) //example: 9.999.999
    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherDetail> voucherDetails = new ArrayList<>();
}
