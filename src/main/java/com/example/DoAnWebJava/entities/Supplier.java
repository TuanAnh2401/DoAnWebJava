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
@Table(name = "tb_Supplier")
@RequiredArgsConstructor
@AllArgsConstructor
public class Supplier extends CommonAbstract {
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
    @NotBlank(message = "Thêm ảnh nhà cung cấp")
    private String image;

    @Column(nullable = false)
    private boolean isActivate;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();
}

