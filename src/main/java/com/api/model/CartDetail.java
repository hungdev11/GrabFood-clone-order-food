package com.api.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "cart_detail", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class CartDetail extends BaseEntity{
    private int quantity;
    private String note;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
