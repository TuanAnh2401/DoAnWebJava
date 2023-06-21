package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.entities.Contact;
import com.example.DoAnWebJava.repositories.AdvRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    @Autowired
    private AdvRepository advRepository;

    public int getTotalAdvs(String searchString) {
        return advRepository.countByTitleContainingIgnoreCase(searchString);
    }

    public List<Adv> getPaginatedContacts(int page, int pageSize, String searchString) {
        // Tính toán vị trí bắt đầu và số lượng liên hệ cần lấy
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Lấy danh sách tất cả liên hệ từ nguồn dữ liệu (database, API, v.v.)
        List<Adv> allAdvs = advRepository.findAll();

        // Lọc danh sách liên hệ dựa trên điều kiện tìm kiếm (nếu có)
        List<Adv> filteredAdvs = filterAdvs(allAdvs, searchString);

        // Kiểm tra và trả về danh sách liên hệ phân trang
        if (startIndex >= filteredAdvs.size()) {
            return Collections.emptyList(); // Không có liên hệ nào để hiển thị
        } else {
            return filteredAdvs.subList(startIndex, Math.min(endIndex, filteredAdvs.size()));
        }
    }

    private List<Adv> filterAdvs(List<Adv> advs, String searchString) {
        return advs.stream()
                .filter(adv -> isContactMatchSearchCriteria(adv, searchString))
                .filter(adv -> !adv.isActivate())
                .collect(Collectors.toList());
    }

    private boolean isContactMatchSearchCriteria(Adv adv, String searchString) {

        String name = adv.getTitle();

        return name.contains(searchString) ;
    }


    public Adv addAdv(Adv model) {
        model.setCreatedDate(new Date());
        model.setModifiedDate(new Date());
        return advRepository.save(model);
    }

    public Adv editAdv(int id) throws UserRegistrationException {
        Optional<Adv> optionalAdv = advRepository.findById(id);
        if (optionalAdv.isPresent()) {
            return optionalAdv.get();
        } else {
            throw new UserRegistrationException("Adv not found with id: " + id);
        }
    }

    public Adv editAdv(int id, Adv model) throws UserRegistrationException {
        Optional<Adv> optionalAdv = advRepository.findById(id);
        if (optionalAdv.isPresent()) {
            Adv adv = optionalAdv.get();
            adv.setTitle(model.getTitle());
            adv.setDescription(model.getDescription());
            adv.setImage(model.getImage());
            adv.setLink(model.getLink());
            adv.setActivate(model.isActivate());
            adv.setModifiedDate(new Date());
            return advRepository.save(adv);
        } else {
            throw new UserRegistrationException("Adv not found with id: " + id);
        }
    }


    public void deleteAdv(int id) throws UserRegistrationException {
        Optional<Adv> optionalAdv = advRepository.findById(id);
        if (optionalAdv.isPresent()) {
            Adv adv = optionalAdv.get();
            adv.setActivate(true);
            advRepository.save(adv);
        } else {
            throw new UserRegistrationException("Adv not found with id: " + id);
        }
    }

    public void deleteAllAdvs(List<Integer> ids) throws UserRegistrationException {
        List<Adv> advList = advRepository.findAllById(ids);
        if (!advList.isEmpty()) {
            advList.forEach(adv -> adv.setActivate(true));
            advRepository.saveAll(advList);
        } else {
            throw new UserRegistrationException("No Advs found with the given ids");
        }
    }

}
