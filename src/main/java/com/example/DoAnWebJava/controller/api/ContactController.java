package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.service.ContactService;
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

    @PutMapping("/edit/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable int id, @Valid @RequestBody Contact updatedContact) {
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            updatedContact.setId(id);
            Contact updated = contactService.updateContact(updatedContact);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            contactService.deleteContact(contact);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
