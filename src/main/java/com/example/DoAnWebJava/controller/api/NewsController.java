package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.NewsDto;
import com.example.DoAnWebJava.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsDto> allNews = newsService.getAllNewsDtos();
        return ResponseEntity.ok(allNews);
    }
    @GetMapping("/getActive/{isActive}")
    public ResponseEntity<List<NewsDto>> getActiveNews(@PathVariable boolean isActive) {
        List<NewsDto> activeNews = newsService.getActiveNewsDtos(isActive);
        return ResponseEntity.ok(activeNews);
    }

    @GetMapping("/getHome/{isHome}")
    public ResponseEntity<List<NewsDto>> getNewsByIsHome(@PathVariable boolean isHome) {
        List<NewsDto> newsByIsHome = newsService.getNewsDtosByIsHome(isHome);
        return ResponseEntity.ok(newsByIsHome);
    }

    @GetMapping("/getSale/{isSale}")
    public ResponseEntity<List<NewsDto>> getNewsByIsSale(@PathVariable boolean isSale) {
        List<NewsDto> newsByIsSale = newsService.getNewsDtosByIsSale(isSale);
        return ResponseEntity.ok(newsByIsSale);
    }

    @GetMapping("/getHot/{isHot}")
    public ResponseEntity<List<NewsDto>> getNewsByIsHot(@PathVariable boolean isHot) {
        List<NewsDto> newsByIsHot = newsService.getNewsDtosByIsHot(isHot);
        return ResponseEntity.ok(newsByIsHot);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable int id) {
        NewsDto news = newsService.getNewsDtoById(id);
        if (news != null) {
            return ResponseEntity.ok(news);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNews(@RequestBody NewsDto newsDto) {
        if (newsDto != null) {
            NewsDto addedNews = newsService.addNews(newsDto);
            return ResponseEntity.ok("News added successfully with ID: " + addedNews.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateNews(@PathVariable int id, @RequestBody NewsDto newsDto) {
        if (newsDto != null) {
            NewsDto updatedNews = newsService.updateNews(id, newsDto);
            if (updatedNews != null) {
                return ResponseEntity.ok("News updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable int id) {
        boolean deleted = newsService.deleteNews(id);
        if (deleted) {
            return ResponseEntity.ok("News deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
