package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    public List<Contact> getContactsByActivate(boolean isActivate) {
        return contactRepository.findByIsActivate(isActivate);
    }

    public Contact getContactById(int id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact createContact(Contact contact) {
        contact.setCreatedDate(new Date());
        contact.setModifiedDate(new Date());
        return contactRepository.save(contact);
    }

    public Contact updateContact(Contact contact) {
        contact.setModifiedDate(new Date());
        return contactRepository.save(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
