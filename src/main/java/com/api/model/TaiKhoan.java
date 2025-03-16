package com.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tai_khoan")
@Data
public class TaiKhoan extends BaseEntity {
    private String username;
    private String matKhau;
}
