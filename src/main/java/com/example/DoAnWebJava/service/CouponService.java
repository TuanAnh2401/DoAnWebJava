package com.example.DoAnWebJava.service;
import com.example.DoAnWebJava.entities.Coupon;
import com.example.DoAnWebJava.repositories.CouponRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CouponService {
    private static final int DEFAULT_PAGE_SIZE = 10;
    @Autowired
    private CouponRepository couponRepository;
    public int getTotalCoupons(String searchString) {
        return couponRepository.countByCodeContainingIgnoreCase(searchString);
    }

    public List<Coupon> getPaginatedContacts(int page, int pageSize, String searchString) {
        // Tính toán vị trí bắt đầu và số lượng liên hệ cần lấy
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Lấy danh sách tất cả liên hệ từ nguồn dữ liệu (database, API, v.v.)
        List<Coupon> allCoupons = couponRepository.findAll();

        // Lọc danh sách liên hệ dựa trên điều kiện tìm kiếm (nếu có)
        List<Coupon> filteredCoupons = filterCoupons(allCoupons, searchString);

        // Kiểm tra và trả về danh sách liên hệ phân trang
        if (startIndex >= filteredCoupons.size()) {
            return Collections.emptyList(); // Không có liên hệ nào để hiển thị
        } else {
            return filteredCoupons.subList(startIndex, Math.min(endIndex, filteredCoupons.size()));
        }
    }

    private List<Coupon> filterCoupons(List<Coupon> coupons, String searchString) {
        return coupons.stream()
                .filter(coupon -> isContactMatchSearchCriteria(coupon, searchString))
                .filter(coupon -> !coupon.isActivate())
                .collect(Collectors.toList());
    }

    private boolean isContactMatchSearchCriteria(Coupon coupon, String searchString) {
        String name = coupon.getCode();
        return name.contains(searchString) ;
    }


    public Coupon addCoupon(Coupon model) {
        model.setCreatedDate(new Date());
        model.setModifiedDate(new Date());
        return couponRepository.save(model);
    }

    public Coupon editCoupon(int id) throws UserRegistrationException {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            return optionalCoupon.get();
        } else {
            throw new UserRegistrationException("Coupon not found with id: " + id);
        }
    }
    public Coupon editCoupon(int id, Coupon model) throws UserRegistrationException {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            coupon.setCode(model.getCode());
            coupon.setDiscount(model.getDiscount());
            coupon.setActivate(model.isActivate());
            coupon.setModifiedDate(new Date());
            return couponRepository.save(coupon);
        } else {
            throw new UserRegistrationException("Coupon not found with id: " + id);
        }
    }

    public void deleteCoupon(int id) throws UserRegistrationException {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            coupon.setActivate(true);
            couponRepository.save(coupon);
        } else {
            throw new UserRegistrationException("Coupon not found with id: " + id);
        }
    }

    public void deleteAllCoupons(List<Integer> ids) throws UserRegistrationException {
        List<Coupon> couponList = couponRepository.findAllById(ids);
        if (!couponList.isEmpty()) {
            couponList.forEach(coupon -> coupon.setActivate(true));
            couponRepository.saveAll(couponList);
        } else {
            throw new UserRegistrationException("No Coupons found with the given ids");
        }
    }
}
