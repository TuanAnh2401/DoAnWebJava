package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.NewsCategory;
import com.example.DoAnWebJava.repositories.NewsCategoryRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    @Autowired
    public NewsCategoryService(NewsCategoryRepository newsCategoryRepository) {
        this.newsCategoryRepository = newsCategoryRepository;
    }

    public List<NewsCategory> getAllNewsCategories() {
        return newsCategoryRepository.findAll();
    }

    public List<NewsCategory> getNewsCategoriesByActivate(boolean isActivate) {
        return newsCategoryRepository.findByIsActivate(isActivate);
    }

    public NewsCategory getNewsCategoryById(int id) {
        return newsCategoryRepository.findById(id).orElse(null);
    }

    public NewsCategory addNewsCategory(NewsCategory newsCategory) {
        newsCategory.setCreatedDate(new Date());
        newsCategory.setModifiedDate(new Date());
        return newsCategoryRepository.save(newsCategory);
    }

    public NewsCategory updateNewsCategory(int id, NewsCategory updatedNewsCategory) throws UserRegistrationException {
        NewsCategory existingNewsCategory = newsCategoryRepository.findById(id).orElse(null);
        if (existingNewsCategory != null) {
            existingNewsCategory.setTitle(updatedNewsCategory.getTitle());
            existingNewsCategory.setActivate(updatedNewsCategory.isActivate());
            existingNewsCategory.setModifiedDate(new Date());
            // Update other properties of NewsCategory as needed
            return newsCategoryRepository.save(existingNewsCategory);
        }
        throw new UserRegistrationException("News Category not found");
    }

    public void deleteNewsCategory(int id) throws UserRegistrationException {
        NewsCategory existingNewsCategory = newsCategoryRepository.findById(id).orElse(null);
        if (existingNewsCategory != null) {
            newsCategoryRepository.delete(existingNewsCategory);
        } else {
            throw new UserRegistrationException("News Category not found");
        }
    }
}
