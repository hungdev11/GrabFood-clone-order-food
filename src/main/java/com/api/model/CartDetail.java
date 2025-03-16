package com.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;


@Data
@Entity
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
