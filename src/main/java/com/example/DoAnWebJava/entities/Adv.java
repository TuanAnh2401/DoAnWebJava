package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_Adv")
@RequiredArgsConstructor
@AllArgsConstructor
public class Adv extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 150)
    @Size(min = 1, max = 150, message = "Bạn không được để trống tiêu đề.")
    @NotBlank(message = "Title must not be blank.")
    private String title;

    @Column(nullable = false, length = 500)
    @Size(min = 1, max = 500, message = "Nội dung quảng cáo không được để trống.")
    @NotBlank(message = "Description must not be blank.")
    private String description;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Thêm ảnh quảng cáo.")
    private String image;

    @Column(length = 500)
    private String link;

    @Column
    private boolean isActivate;

}

