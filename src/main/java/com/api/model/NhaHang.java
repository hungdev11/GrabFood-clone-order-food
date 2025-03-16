package com.api.model;

import com.api.utility.TrangThaiNhaHang;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "nha_hang")
public class NhaHang extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String ten;

    @Column(nullable = false)
    private String hinhAnh;

    @Column(nullable = false)
    private LocalTime gioMoCua;

    @Column(nullable = false)
    private LocalTime gioDongCua;

    @Column(nullable = false)
    private LocalDate ngayTao;

    @Column(columnDefinition = "VARCHAR(100)")
    private String moTa;

    @Column(nullable = false)
    private TrangThaiNhaHang trangThai;

    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private DiaChi diaChi;

    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private TaiKhoan taiKhoan;

    @PrePersist
    private void onCreate() {
        if (this.ngayTao == null) {
            this.ngayTao = LocalDate.now();
        }
    }
}
