package com.example.DoAnWebJava.dto;

import com.example.DoAnWebJava.support.CommonAbstractDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends CommonAbstractDto {
    private int id;
    private String title;
    private String description;
    private String detail;
    private String image;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private BigDecimal priceSale;
    private int quantity;
    private boolean isHome;
    private boolean isSale;
    private boolean isHot;
    private boolean isActivate;
    private boolean isStatus;
    private int productCategoryId;
    private int supplierId;
}
