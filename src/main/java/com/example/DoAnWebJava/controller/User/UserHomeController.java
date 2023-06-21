
package com.example.DoAnWebJava.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserHomeController {
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Trang chủ");
        return "user/home/index";
    }
}