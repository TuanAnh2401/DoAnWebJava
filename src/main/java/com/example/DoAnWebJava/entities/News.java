package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import com.example.DoAnWebJava.validators.annotations.AllowHtml;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_News")
@RequiredArgsConstructor
@AllArgsConstructor
public class News extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Bạn không được để trống tiêu đề tin")
    @Size(max = 150)
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Nội dung tin tức không được để trống")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Chi tiết tin tức không được để trống")
    @AllowHtml
    private String detail;

    @Column(nullable = false)
    @NotBlank(message = "Thêm ảnh tin tức.")
    private String image;

    @Column
    private boolean isHome;

    @Column
    private boolean isSale;

    @Column
    private boolean isHot;

    @Column
    private boolean isActivate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsCategoryId", referencedColumnName = "id")
    private NewsCategory newsCategory;

}

