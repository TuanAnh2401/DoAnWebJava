package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.ContactService;
import com.example.DoAnWebJava.support.ResponsePaging;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/paginate")
    public ResponseEntity<ResponsePaging<List<Contact>>> getPaginatedContacts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchString
    ) {
        int pageSize = 10; // Kích thước trang (số lượng liên hệ trên mỗi trang)
        int totalContacts = contactService.getTotalContacts(searchString);
        int totalPages = (int) Math.ceil((double) totalContacts / pageSize);

        // Giới hạn số trang hiện tại trong khoảng từ 1 đến tổng số trang
        page = Math.max(1, Math.min(page, totalPages));

        // Lấy danh sách liên hệ phân trang từ Service
        List<Contact> contacts = contactService.getPaginatedContacts(page, pageSize, searchString);

        // Tạo đối tượng ResponsePaging để chứa thông tin phân trang và danh sách liên hệ
        ResponsePaging<List<Contact>> responsePaging = new ResponsePaging<>(contacts, totalPages, page, totalContacts);

        return ResponseEntity.ok(responsePaging);
    }

    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<Contact>> getContactsByActivate(@PathVariable boolean isActivate) {
        List<Contact> contacts = contactService.getContactsByActivate(isActivate);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact createdContact = contactService.createContact(contact);
        return ResponseEntity.ok(createdContact);
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.noContent().build();
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAllContacts(@RequestParam("ids") List<Integer> ids) {
        try {
            contactService.deleteAllContacts(ids);
            return ResponseEntity.noContent().build();
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}