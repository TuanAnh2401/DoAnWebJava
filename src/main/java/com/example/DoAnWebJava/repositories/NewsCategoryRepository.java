package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
    List<NewsCategory> findByIsActivate(boolean isActivate);
}
