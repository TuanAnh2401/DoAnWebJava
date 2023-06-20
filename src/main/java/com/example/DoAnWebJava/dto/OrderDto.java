package com.example.DoAnWebJava.dto;

import com.example.DoAnWebJava.entities.OrderDetail;
import com.example.DoAnWebJava.entities.User;
import com.example.DoAnWebJava.support.CommonAbstractDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends CommonAbstractDto {
    private int id;
    private BigDecimal totalAmount;
    private int quantity;
    private int typePayment;
    private boolean isOrder;
    private Set<OrderDetail> orderDetails = new HashSet<>();
    private User user;
}
