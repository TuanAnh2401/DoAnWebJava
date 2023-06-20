package com.example.DoAnWebJava.dto;

import com.example.DoAnWebJava.support.CommonAbstractDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto extends CommonAbstractDto {
    private int id;
    private String title;
    private String description;
    private String detail;
    private String image;
    private boolean isHome;
    private boolean isSale;
    private boolean isHot;
    private boolean isActivate;
    private int newsCategoryId;
}
