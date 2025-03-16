package com.api.model;

import com.api.utils.RestaurantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, name = "opening_hour")
    private LocalTime openingHour;

    @Column(nullable = false, name = "closing_hour")
    private LocalTime closingHour;

    @Column(nullable = false, name = "create_date")
    private LocalDate createDate;

    @Column(columnDefinition = "VARCHAR(100)")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> vouchers = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @PrePersist
    private void onCreate() {
        if (this.createDate == null) {
            this.createDate = LocalDate.now();
        }
    }
}
