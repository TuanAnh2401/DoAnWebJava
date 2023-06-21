package com.example.DoAnWebJava.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/users/reset-password")
public class ResetpasswordController {
    @GetMapping
    public String Resetpassword(Model model) {
        model.addAttribute("pageTitle", "Resetpassword");
        return "/account/reset-password";
    }
}
