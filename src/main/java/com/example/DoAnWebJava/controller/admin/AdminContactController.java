package com.example.DoAnWebJava.controller.admin;

import com.example.DoAnWebJava.entities.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminContactController {
    @GetMapping("/contact")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý Liên hệ");
        return "admin/contact/index";
    }
    @GetMapping("/contact/detail/{id}")
    public String getContactDetail(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        model.addAttribute("pageTitle", "Chi tiết");

        return "admin/contact/detail";
    }
}
