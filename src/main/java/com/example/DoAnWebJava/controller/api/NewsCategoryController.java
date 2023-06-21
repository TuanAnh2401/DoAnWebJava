package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.NewsCategoryDto;
import com.example.DoAnWebJava.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<NewsCategoryDto>> getAllNewsCategories() {
        List<NewsCategoryDto> newsCategoryDtos = newsCategoryService.getAllNewsCategoryDtos();
        return new ResponseEntity<>(newsCategoryDtos, HttpStatus.OK);
    }

    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<NewsCategoryDto>> getNewsCategoriesByActivate(@PathVariable boolean isActivate) {
        List<NewsCategoryDto> activeNewsCategoryDtos = newsCategoryService.getNewsCategoryDtosByActivate(isActivate);
        return new ResponseEntity<>(activeNewsCategoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryDto> getNewsCategoryById(@PathVariable int id) {
        NewsCategoryDto newsCategoryDto = newsCategoryService.getNewsCategoryDtoById(id);
        if (newsCategoryDto != null) {
            return new ResponseEntity<>(newsCategoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<NewsCategoryDto> addNewsCategory(@RequestBody NewsCategoryDto newsCategoryDto) {
        NewsCategoryDto addedNewsCategory = newsCategoryService.addNewsCategory(newsCategoryDto);
        return new ResponseEntity<>(addedNewsCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NewsCategoryDto> updateNewsCategory(@PathVariable int id, @RequestBody NewsCategoryDto newsCategoryDto) {
        NewsCategoryDto updatedNewsCategory = newsCategoryService.updateNewsCategory(id, newsCategoryDto);
        if (updatedNewsCategory != null) {
            return new ResponseEntity<>(updatedNewsCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNewsCategory(@PathVariable int id) {
        boolean deleted = newsCategoryService.deleteNewsCategory(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
