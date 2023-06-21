package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.Adv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AdvRepository extends JpaRepository<Adv, Integer> {

    int countByTitleContainingIgnoreCase(String searchString);
    List<Adv> findByIsActivate(boolean isActivate);
}
