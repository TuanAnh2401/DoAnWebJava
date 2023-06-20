package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByIsActivate(boolean isActivate);
}
