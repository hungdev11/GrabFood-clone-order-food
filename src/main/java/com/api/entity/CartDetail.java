package com.api.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(
        name = "cart_detail",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cart", "food", "order"})
        }
)
//@Check(constraints = "quantity > 0")
public class CartDetail extends BaseEntity{

    @Column(nullable = false)
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
