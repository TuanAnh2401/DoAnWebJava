package com.example.DoAnWebJava.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SettingSystemController {
    // GET: /admin/SettingSystem
    @GetMapping("/settingSystem")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Cấu hình hệ thống");
        return "admin/settingSystem/index";
    }

    // GET: /admin/SettingSystem/Partial_Setting
    @GetMapping("/settingSystem/Partial_Setting")
    public String partialSetting() {
        return "admin/settingSystem/_partialSetting";
    }
}

