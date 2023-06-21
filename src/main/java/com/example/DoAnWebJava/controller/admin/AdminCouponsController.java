package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminCouponsController {
    @GetMapping("/coupon")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý khuyến mãi");
        return "admin/coupon/index";
    }
    @GetMapping("/coupon/add")
    public String Add(Model model) {
        model.addAttribute("pageTitle", "Thêm mới");

        return "admin/coupon/add";
    }
    @GetMapping("/coupon/edit/{id}")
    public String getCouponDetail(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        model.addAttribute("pageTitle", "Cập nhật");

        return "admin/coupon/edit";
    }
}
