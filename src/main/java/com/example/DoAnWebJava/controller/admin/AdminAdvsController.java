package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminAdvsController {
    @GetMapping("/adv")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý Quảng cáo");
        return "admin/advs/index";
    }
    @GetMapping("/adv/edit/{id}")
    public String getAdvDetail(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        model.addAttribute("pageTitle", "Cập nhật");

        return "admin/advs/edit";
    }
    @GetMapping("/adv/add")
    public String Add(Model model) {
        model.addAttribute("pageTitle", "Thêm mới");

        return "admin/advs/add";
    }
}
