package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
