package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {
    @GetMapping("/account")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Quản lý Quảng cáo");
        return "admin/account/index";
    }
}
