package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_OrderDetail")
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDetail extends CommonAbstract{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}

