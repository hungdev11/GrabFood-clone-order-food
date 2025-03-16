package com.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "food_detail")
public class FoodDetail extends BaseEntity{

    @Column(nullable = false, precision = 9, scale = 2) //example: 9.999.999,99
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
