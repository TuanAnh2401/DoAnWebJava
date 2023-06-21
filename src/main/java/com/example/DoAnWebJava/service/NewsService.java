package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.NewsDto;
import com.example.DoAnWebJava.entities.News;
import com.example.DoAnWebJava.entities.NewsCategory;
import com.example.DoAnWebJava.repositories.NewsCategoryRepository;
import com.example.DoAnWebJava.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;
    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    public int getTotalNews(String searchString, boolean isActivate) {
        if (isActivate) {
            return newsRepository.countByTitleContainingIgnoreCaseAndIsActivate(searchString, true);
        } else {
            return newsRepository.countByTitleContainingIgnoreCase(searchString);
        }
    }

    public List<NewsDto> getPaginatedNews(int page, int pageSize, String searchString, boolean isActivate ) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        List<News> allNews = newsRepository.findAll();
        List<News> filteredNews = filterNews(allNews, searchString, isActivate);

        if (startIndex >= filteredNews.size()) {
            return Collections.emptyList();
        } else {
            List<News> paginatedNews = filteredNews.subList(startIndex, Math.min(endIndex, filteredNews.size()));
            return convertToDtoList(paginatedNews);
        }
    }

    private List<News> filterNews(List<News> newsList, String searchString, boolean isActivate) {
        return newsList.stream()
                .filter(news -> isNewsMatchSearchCriteria(news, searchString))
                .filter(news -> news.isActivate() == isActivate )
                .collect(Collectors.toList());
    }

    private boolean isNewsMatchSearchCriteria(News news, String searchString) {
        String title = news.getTitle();
        return title.contains(searchString);
    }
    public List<NewsDto> getAllNewsDtos() {
        List<News> newsList = newsRepository.findAll();
        return convertToDtoList(newsList);
    }


    public NewsDto getNewsDtoById(int id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        return newsOptional.map(this::convertToDto).orElse(null);
    }

    public NewsDto addNews(NewsDto newsDto) {
        News news = convertToEntity(newsDto);
        news.setCreatedDate(new Date());
        news.setModifiedDate(new Date());
        News addedNews = newsRepository.save(news);
        return convertToDto(addedNews);
    }

    public NewsDto updateNews(int id, NewsDto newsDto) {
        Optional<News> existingNewsOptional = newsRepository.findById(id);
        if (existingNewsOptional.isPresent()) {
            News existingNews = existingNewsOptional.get();
            News updatedNews = convertToEntity(newsDto);
            existingNews.setTitle(updatedNews.getTitle());
            existingNews.setDescription(updatedNews.getDescription());
            existingNews.setDetail(updatedNews.getDetail());
            existingNews.setImage(updatedNews.getImage());
            existingNews.setHome(updatedNews.isHome());
            existingNews.setSale(updatedNews.isSale());
            existingNews.setHot(updatedNews.isHot());
            existingNews.setActivate(updatedNews.isActivate());
            existingNews.setModifiedDate(new Date());
            existingNews.setNewsCategory(updatedNews.getNewsCategory());
            News savedNews = newsRepository.save(existingNews);
            return convertToDto(savedNews);
        }
        return null;
    }

    public boolean deleteNews(int id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<NewsDto> convertToDtoList(List<News> newsList) {
        return newsList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NewsDto convertToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setDescription(news.getDescription());
        newsDto.setDetail(news.getDetail());
        newsDto.setImage(news.getImage());
        newsDto.setHome(news.isHome());
        newsDto.setSale(news.isSale());
        newsDto.setHot(news.isHot());
        newsDto.setActivate(news.isActivate());
        newsDto.setCreatedDate(news.getCreatedDate());
        newsDto.setModifiedDate(news.getModifiedDate());
        newsDto.setNewsCategoryId(news.getNewsCategory().getId());
        return newsDto;
    }

    private News convertToEntity(NewsDto newsDto) {
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setDescription(newsDto.getDescription());
        news.setDetail(newsDto.getDetail());
        news.setImage(newsDto.getImage());
        news.setHome(newsDto.isHome());
        news.setSale(newsDto.isSale());
        news.setHot(newsDto.isHot());
        news.setActivate(newsDto.isActivate());
        news.setCreatedDate(newsDto.getCreatedDate());
        news.setModifiedDate(newsDto.getModifiedDate());
        Optional<NewsCategory> newsCategoryOptional = newsCategoryRepository.findById(newsDto.getNewsCategoryId());
        if (newsCategoryOptional.isPresent()) {
            news.setNewsCategory(newsCategoryOptional.get());
        }
        return news;
    }
}
