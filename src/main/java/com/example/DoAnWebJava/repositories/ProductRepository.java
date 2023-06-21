package com.example.DoAnWebJava.repositories;
import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    int countByTitleContainingIgnoreCaseAndIsActivate(String searchString, boolean isActivate);

    int countByTitleContainingIgnoreCase(String searchString);

}
