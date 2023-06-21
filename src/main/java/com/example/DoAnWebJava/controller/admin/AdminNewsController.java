package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminNewsController {
    @GetMapping("/new")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý Tin tức");
        return "admin/news/index";
    }
    @GetMapping("/new/detail/{id}")
    public String getNewsDetail(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        model.addAttribute("pageTitle", "Chi tiết");

        return "admin/news/detail";
    }
}
