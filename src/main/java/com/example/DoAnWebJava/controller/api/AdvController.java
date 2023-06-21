package com.example.DoAnWebJava.controller.api;


import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.AdvService;
import com.example.DoAnWebJava.support.ResponsePaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/adv")
public class AdvController {

    @Autowired
    private AdvService advService;

    @GetMapping("/paginate")
    public ResponseEntity<ResponsePaging<List<Adv>>> getPaginatedAdvs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchString
    ) {
        int pageSize = 10; // Kích thước trang (số lượng liên hệ trên mỗi trang)
        int totalAdvs = advService.getTotalAdvs(searchString);
        int totalPages = (int) Math.ceil((double) totalAdvs / pageSize);

        // Giới hạn số trang hiện tại trong khoảng từ 1 đến tổng số trang
        page = Math.max(1, Math.min(page, totalPages));

        // Lấy danh sách liên hệ phân trang từ Service
        List<Adv> advs = advService.getPaginatedContacts(page, pageSize, searchString);

        // Tạo đối tượng ResponsePaging để chứa thông tin phân trang và danh sách liên hệ
        ResponsePaging<List<Adv>> responsePaging = new ResponsePaging<>(advs, totalPages, page, totalAdvs);

        return ResponseEntity.ok(responsePaging);
    }


    @PostMapping("/add")
    public ResponseEntity<String> addAdv(@RequestBody Adv model) {
        if (model != null) {
            Adv addedAdv = advService.addAdv(model);
            return ResponseEntity.ok("Adv added successfully with ID: " + addedAdv.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adv> editAdv(@PathVariable int id) {
        try {
            Adv adv = advService.editAdv(id);
            return ResponseEntity.ok(adv);
        } catch (UserRegistrationException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateAdv(@PathVariable int id, @RequestBody Adv model) {
        if (model != null) {
            try {
                Adv updatedAdv = advService.editAdv(id, model);
                return ResponseEntity.ok("Adv updated successfully");
            } catch (UserRegistrationException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdv(@PathVariable int id) {
        try {
            advService.deleteAdv(id);
            return ResponseEntity.noContent().build();
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAllAdvs(@RequestParam("ids") List<Integer> ids) {
        try {
            advService.deleteAllAdvs(ids);
            return ResponseEntity.noContent().build();
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
