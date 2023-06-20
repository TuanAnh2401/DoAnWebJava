package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.entities.NewsCategory;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news-categories")
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    @Autowired
    public NewsCategoryController(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NewsCategory>> getAllNewsCategories() {
        List<NewsCategory> allNewsCategories = newsCategoryService.getAllNewsCategories();
        return ResponseEntity.ok(allNewsCategories);
    }

    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<NewsCategory>> getNewsCategoriesByActivate(@PathVariable boolean isActivate) {
        List<NewsCategory> activeNewsCategories = newsCategoryService.getNewsCategoriesByActivate(isActivate);
        return ResponseEntity.ok(activeNewsCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategory> getNewsCategoryById(@PathVariable int id) {
        NewsCategory newsCategory = newsCategoryService.getNewsCategoryById(id);
        if (newsCategory != null) {
            return ResponseEntity.ok(newsCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewsCategory(@RequestBody NewsCategory newsCategory) {
        if (newsCategory != null) {
            NewsCategory addedNewsCategory = newsCategoryService.addNewsCategory(newsCategory);
            return ResponseEntity.ok("News Category added successfully with ID: " + addedNewsCategory.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateNewsCategory(@PathVariable int id, @RequestBody NewsCategory newsCategory) throws UserRegistrationException {
        if (newsCategory != null) {
            NewsCategory updatedNewsCategory = newsCategoryService.updateNewsCategory(id, newsCategory);
            return ResponseEntity.ok("News Category updated successfully");
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNewsCategory(@PathVariable int id) {
        try {
            newsCategoryService.deleteNewsCategory(id);
            return ResponseEntity.ok("News Category deleted successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
