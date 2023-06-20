package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.Order;
import com.example.DoAnWebJava.entities.OrderDetail;
import com.example.DoAnWebJava.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByIsActivate(boolean isActivate);
}
