package com.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dia_chi")
public class DiaChi extends BaseEntity{
    private String tinh;
    private String huyen;
    private String xa;
    private String chiTiet;
    private boolean laMacDinh;
}
