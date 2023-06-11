package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_Coupon")
@RequiredArgsConstructor
@AllArgsConstructor
public class Coupon extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Bạn không được để trống mã code.")
    private String code;

    @Column(nullable = false)
    @NotNull(message = "Không được để trống khuyến mãi.")
    @Min(value = 1, message = "Khuyến mãi phải lớn hơn hoặc bằng 1.")
    @Max(value = 100, message = "Khuyến mãi phải nhỏ hơn hoặc bằng 100.")
    private int discount;

    @Column
    private boolean isActivate;

}

