package com.example.DoAnWebJava.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class LoginController {
    @GetMapping("/login")
    public String Login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "user/account/login";
    }
    @GetMapping("/register")
    public String Register(Model model) {
        model.addAttribute("pageTitle", "Register");
        return "user/account/register";
    }
    @GetMapping("/forgot-password")
    public String Forgotpass(Model model) {
        model.addAttribute("pageTitle", "Forgotpass");
        return "user/account/forgot-password";
    }

}
