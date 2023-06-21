package com.example.DoAnWebJava.dto;

import com.example.DoAnWebJava.support.CommonAbstractDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsCategoryDto extends CommonAbstractDto {
    private int id;
    private String title;
    private boolean isActivate;

    public NewsCategoryDto(Date createdDate, Date modifiedDate, int id, String title, boolean isActivate) {
        super(createdDate, modifiedDate);
        this.id = id;
        this.title = title;
        this.isActivate = isActivate;
    }
}
