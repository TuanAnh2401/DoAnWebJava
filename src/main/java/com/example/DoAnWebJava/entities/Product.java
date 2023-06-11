package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import com.example.DoAnWebJava.validators.annotations.AllowHtml;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_Product")
@RequiredArgsConstructor
@AllArgsConstructor
public class Product extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 250)
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Nội dung không được để trống")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Chi tiết không được để trống.")
    @AllowHtml
    private String detail;

    @Column(length = 250)
    private String image;

    @Column(nullable = false)
    @NotBlank(message = "Giá nhập không được để trống")
    @DecimalMin(value = "0.00", inclusive = false, message = "Giá nhập phải lớn hơn 0.")
    private BigDecimal originalPrice;

    @Column(nullable = false)
    @NotBlank(message = "Giá không được để trống")
    @DecimalMin(value = "0.00", inclusive = false, message = "Giá phải lớn hơn 0.")
    private BigDecimal price;

    @DecimalMin(value = "0.00", inclusive = true, message = "Giá khuyến mãi phải lớn hơn hoặc bằng 0.")
    private BigDecimal priceSale;

    @Column(nullable = false)
    @NotBlank(message = "Số lượng không được để trống")
    @Range(min = 0, message = "Số lượng phải lớn hơn hoặc bằng 0.")
    private int quantity;

    @Column(nullable = false)
    private boolean isHome;

    @Column
    private boolean isSale;

    @Column
    private boolean isHot;

    @Column
    private boolean isActivate;

    @Column
    private boolean isStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCategoryId", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierId", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductImage> productImage = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();
}

