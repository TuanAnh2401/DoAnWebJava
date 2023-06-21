package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.NewsCategoryDto;
import com.example.DoAnWebJava.entities.NewsCategory;
import com.example.DoAnWebJava.repositories.NewsCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    @Autowired
    public NewsCategoryService(NewsCategoryRepository newsCategoryRepository) {
        this.newsCategoryRepository = newsCategoryRepository;
    }

    public List<NewsCategoryDto> getAllNewsCategoryDtos() {
        List<NewsCategory> newsCategories = newsCategoryRepository.findAll();
        return newsCategories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<NewsCategoryDto> getNewsCategoryDtosByActivate(boolean isActivate) {
        List<NewsCategory> activeNewsCategories = newsCategoryRepository.findByIsActivate(isActivate);
        return activeNewsCategories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public NewsCategoryDto getNewsCategoryDtoById(int id) {
        Optional<NewsCategory> newsCategoryOptional = newsCategoryRepository.findById(id);
        return newsCategoryOptional.map(this::convertToDto).orElse(null);
    }

    public NewsCategoryDto addNewsCategory(NewsCategoryDto newsCategoryDto) {
        NewsCategory newsCategory = convertToEntity(newsCategoryDto);
        NewsCategory savedNewsCategory = newsCategoryRepository.save(newsCategory);
        return convertToDto(savedNewsCategory);
    }

    public NewsCategoryDto updateNewsCategory(int id, NewsCategoryDto newsCategoryDto) {
        Optional<NewsCategory> newsCategoryOptional = newsCategoryRepository.findById(id);
        if (newsCategoryOptional.isPresent()) {
            NewsCategory newsCategory = newsCategoryOptional.get();
            newsCategory.setTitle(newsCategoryDto.getTitle());
            newsCategory.setActivate(newsCategoryDto.isActivate());
            NewsCategory updatedNewsCategory = newsCategoryRepository.save(newsCategory);
            return convertToDto(updatedNewsCategory);
        }
        return null;
    }

    public boolean deleteNewsCategory(int id) {
        Optional<NewsCategory> newsCategoryOptional = newsCategoryRepository.findById(id);
        if (newsCategoryOptional.isPresent()) {
            newsCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private NewsCategoryDto convertToDto(NewsCategory newsCategory) {
        NewsCategoryDto newsCategoryDto = new NewsCategoryDto();
        newsCategoryDto.setId(newsCategory.getId());
        newsCategoryDto.setTitle(newsCategory.getTitle());
        newsCategoryDto.setActivate(newsCategory.isActivate());
        return newsCategoryDto;
    }

    private NewsCategory convertToEntity(NewsCategoryDto newsCategoryDto) {
        NewsCategory newsCategory = new NewsCategory();
        newsCategory.setId(newsCategoryDto.getId());
        newsCategory.setTitle(newsCategoryDto.getTitle());
        newsCategory.setActivate(newsCategoryDto.isActivate());
        return newsCategory;
    }
}
