package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    int countByTitleContainingIgnoreCaseAndIsActivate(String searchString, boolean isActivate);

    int countByTitleContainingIgnoreCase(String searchString);
}
