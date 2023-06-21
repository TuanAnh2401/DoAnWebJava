package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.NewsDto;
import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.service.NewsService;
import com.example.DoAnWebJava.support.ResponsePaging;
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

    @GetMapping("/paginate")
    public ResponseEntity<ResponsePaging<List<NewsDto>>> getPaginatedContacts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "true") boolean isActivate
    ) {
        int pageSize = 10; // Kích thước trang (số lượng liên hệ trên mỗi trang)
        int totalContacts = newsService.getTotalNews(searchString, isActivate);
        int totalPages = (int) Math.ceil((double) totalContacts / pageSize);

        // Giới hạn số trang hiện tại trong khoảng từ 1 đến tổng số trang
        page = Math.max(1, Math.min(page, totalPages));

        // Lấy danh sách liên hệ phân trang từ Service
        List<NewsDto> contacts = newsService.getPaginatedNews(page, pageSize, searchString, isActivate);

        // Tạo đối tượng ResponsePaging để chứa thông tin phân trang và danh sách liên hệ
        ResponsePaging<List<NewsDto>> responsePaging = new ResponsePaging<>(contacts, totalPages, page, totalContacts);

        return ResponseEntity.ok(responsePaging);
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
