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
@Table(name = "tb_NewsCategory")
@RequiredArgsConstructor
@AllArgsConstructor
public class NewsCategory extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Bạn không được để trống tiêu đề tin")
    @Size(max = 150)
    private String title;
    @Column
    private boolean isActivate;
    @OneToMany(mappedBy = "newsCategory", cascade = CascadeType.ALL)
    private Set<News> news = new HashSet<>();

}
