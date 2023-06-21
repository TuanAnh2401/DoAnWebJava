package com.example.DoAnWebJava.repositories;
import com.example.DoAnWebJava.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    int countByCodeContainingIgnoreCase(String searchString);
    List<Coupon> findByIsActivate(boolean isActivate);

}
