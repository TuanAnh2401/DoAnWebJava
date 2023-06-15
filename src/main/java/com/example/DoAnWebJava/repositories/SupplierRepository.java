package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    // Custom methods if needed
    List<Supplier> findByIsActivate(boolean isActivate);
}
