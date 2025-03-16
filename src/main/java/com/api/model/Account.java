package com.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Account extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;
}
