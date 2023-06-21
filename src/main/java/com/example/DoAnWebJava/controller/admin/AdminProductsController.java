package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
public class AdminProductsController {
    @GetMapping
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý Sản phẩm");
        return "admin/products/index";
    }
    @GetMapping("/edit/{id}")
    public String getAdvDetail(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        model.addAttribute("pageTitle", "Cập nhật");

        return "admin/products/edit";
    }
    @GetMapping("/add")
    public String Add(Model model) {
        model.addAttribute("pageTitle", "Thêm mới");

        return "admin/products/add";
    }
}
