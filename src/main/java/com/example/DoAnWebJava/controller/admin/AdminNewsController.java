package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {
    @GetMapping
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý Tin tức");
        return "admin/news/index";
    }
    @GetMapping("/detail/{id}")
    public String getNewsDetail(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        model.addAttribute("pageTitle", "Chi tiết");

        return "admin/news/detail";
    }
    @GetMapping("/add")
    public String Add(Model model) {
        model.addAttribute("pageTitle", "Thêm mới");

        return "admin/news/add";
    }
}
