package com.example.DoAnWebJava.dto;

import com.example.DoAnWebJava.entities.Order;
import com.example.DoAnWebJava.entities.Product;
import com.example.DoAnWebJava.support.CommonAbstractDto;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto extends CommonAbstractDto{
    private int id;
    private BigDecimal price;
    private int quantity;
    private Order order;
    private Product product;
}
