package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_ProductCategory")
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductCategory extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 150)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Thêm ảnh sản phẩm")
    private String image;

    @Column
    private boolean isActivate;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

}

