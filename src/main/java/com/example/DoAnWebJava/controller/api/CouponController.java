package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.entities.Coupon;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.CouponService;
import com.example.DoAnWebJava.support.ResponsePaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/paginate")
    public ResponseEntity<ResponsePaging<List<Coupon>>> getPaginatedCoupons(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchString
    ) {
        int pageSize = 10; // Kích thước trang (số lượng liên hệ trên mỗi trang)
        int totalCoupons = couponService.getTotalCoupons(searchString);
        int totalPages = (int) Math.ceil((double) totalCoupons / pageSize);

        // Giới hạn số trang hiện tại trong khoảng từ 1 đến tổng số trang
        page = Math.max(1, Math.min(page, totalPages));

        // Lấy danh sách liên hệ phân trang từ Service
        List<Coupon> coupons = couponService.getPaginatedContacts(page, pageSize, searchString);

        // Tạo đối tượng ResponsePaging để chứa thông tin phân trang và danh sách liên hệ
        ResponsePaging<List<Coupon>> responsePaging = new ResponsePaging<>(coupons, totalPages, page, totalCoupons);

        return ResponseEntity.ok(responsePaging);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addCoupon(@RequestBody Coupon model) {
        if (model != null) {
            Coupon addCoupon = couponService.addCoupon(model);
            return ResponseEntity.ok("Coupon added successfully with ID: " + addCoupon.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> editCoupon(@PathVariable int id) {
        try {
            Coupon coupon = couponService.editCoupon(id);
            return ResponseEntity.ok(coupon);
        } catch (UserRegistrationException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateCoupon(@PathVariable int id, @RequestBody Coupon model) {
        if (model != null) {
            try {
                Coupon updatedCoupon = couponService.editCoupon(id, model);
                return ResponseEntity.ok("Coupon updated successfully");
            } catch (UserRegistrationException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int id) {
        try {
            couponService.deleteCoupon(id);
            return ResponseEntity.noContent().build();
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCoupons(@RequestParam("ids") List<Integer> ids) {
        try {
            couponService.deleteAllCoupons(ids);
            return ResponseEntity.noContent().build();
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

