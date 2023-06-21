package com.example.DoAnWebJava.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class LoginController {
    @GetMapping("/login")
    public String Login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "user/account/login";
    }
}
