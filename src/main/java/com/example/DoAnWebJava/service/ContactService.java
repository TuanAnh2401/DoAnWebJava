package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.repositories.ContactRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public int getTotalContacts(String searchString) {
        return contactRepository.countByNameContainingIgnoreCase(searchString);
    }

    public List<Contact> getPaginatedContacts(int page, int pageSize, String searchString) {
        // Tính toán vị trí bắt đầu và số lượng liên hệ cần lấy
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Lấy danh sách tất cả liên hệ từ nguồn dữ liệu (database, API, v.v.)
        List<Contact> allContacts = contactRepository.findAll();

        // Lọc danh sách liên hệ dựa trên điều kiện tìm kiếm (nếu có)
        List<Contact> filteredContacts = filterContacts(allContacts, searchString);

        // Kiểm tra và trả về danh sách liên hệ phân trang
        if (startIndex >= filteredContacts.size()) {
            return Collections.emptyList(); // Không có liên hệ nào để hiển thị
        } else {
            return filteredContacts.subList(startIndex, Math.min(endIndex, filteredContacts.size()));
        }
    }

    private List<Contact> filterContacts(List<Contact> contacts, String searchString) {
        return contacts.stream()
                .filter(contact -> isContactMatchSearchCriteria(contact, searchString))
                .collect(Collectors.toList());
    }

    private boolean isContactMatchSearchCriteria(Contact contact, String searchString) {
        // Kiểm tra xem tên khách hàng hoặc email có chứa chuỗi tìm kiếm hay không
        String name = contact.getName();
        String email = contact.getEmail();

        return name.contains(searchString) || email.contains(searchString);
    }


    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> getContactsByActivate(boolean isActivate) {
        return contactRepository.findByIsActivate(isActivate);
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
