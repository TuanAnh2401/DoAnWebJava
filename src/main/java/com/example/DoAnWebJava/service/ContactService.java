package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.repositories.ContactRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private ContactRepository contactRepository;

    public int getTotalContacts(String searchString, boolean isActivate) {
        if (isActivate) {
            return contactRepository.countByNameContainingIgnoreCaseAndIsActivate(searchString, true);
        } else {
            return contactRepository.countByNameContainingIgnoreCase(searchString);
        }
    }
    public List<Contact> getPaginatedContacts(int page, int pageSize, String searchString, boolean isActivate) {
        // Tính toán vị trí bắt đầu và số lượng liên hệ cần lấy
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Lấy danh sách tất cả liên hệ từ nguồn dữ liệu (database, API, v.v.)
        List<Contact> allAdvs = contactRepository.findAll();

        // Lọc danh sách liên hệ dựa trên điều kiện tìm kiếm (nếu có) và trạng thái isActivate
        List<Contact> filteredAdvs = filterContacts(allAdvs, searchString, isActivate);

        // Kiểm tra và trả về danh sách liên hệ phân trang
        if (startIndex >= filteredAdvs.size()) {
            return Collections.emptyList(); // Không có liên hệ nào để hiển thị
        } else {
            return filteredAdvs.subList(startIndex, Math.min(endIndex, filteredAdvs.size()));
        }
    }
    private List<Contact> filterContacts(List<Contact> contacts, String searchString, boolean isActivate) {
        return contacts.stream()
                .filter(contact -> isContactMatchSearchCriteria(contact, searchString))
                .filter(contact -> contact.isActivate() == isActivate)
                .collect(Collectors.toList());
    }

    private boolean isContactMatchSearchCriteria(Contact contact, String searchString) {

        String name = contact.getName();
        String email = contact.getEmail();

        return name.contains(searchString) || email.contains(searchString) ;
    }


    public Contact getContactById(int id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        Contact ct = new Contact();
        if (optionalContact.isPresent()) {
            ct = optionalContact.get();
            ct.setRead(true);
            ct.setModifiedDate(new Date());
            return contactRepository.save(ct);
        }
        return ct;
    }

    public Contact createContact(Contact contact) {
        contact.setCreatedDate(new Date());
        contact.setModifiedDate(new Date());
        return contactRepository.save(contact);
    }

    public Contact updateContact (int id) throws UserRegistrationException {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact ct = optionalContact.get();
            ct.setRead(true);
            ct.setModifiedDate(new Date());
            return contactRepository.save(ct);
        } else {
            throw new UserRegistrationException("Contact not found with id: " + id);
        }
    }

    public void deleteContact(int id) throws UserRegistrationException {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact ct = optionalContact.get();
            ct.setActivate(false);
            contactRepository.save(ct);
        } else {
            throw new UserRegistrationException("Contact not found with id: " + id);
        }
    }
    public void deleteAllContacts(List<Integer> cts) throws UserRegistrationException {
        List<Contact> contactList = contactRepository.findAllById(cts);
        if (!contactList.isEmpty()) {
            contactList.forEach(ct -> ct.setActivate(false));
            contactRepository.saveAll(contactList);
        } else {
            throw new UserRegistrationException("No Contacts found with the given ids");
        }
    }
}
