package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    // Các phương thức tùy chỉnh nếu cần thiết
    List<ProductCategory> findByIsActivate(boolean isActivate);
}
