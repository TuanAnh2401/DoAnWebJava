package com.example.DoAnWebJava.controller;

import com.example.DoAnWebJava.dto.SettingSystemDto;
import com.example.DoAnWebJava.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setting")
public class SystemSettingController {
    @Autowired
    private SystemSettingService systemSettingService;
    @GetMapping("/get")
    public ResponseEntity<SettingSystemDto> getSettings() {
        SettingSystemDto settings = new SettingSystemDto();
        settings.setSettingTitle(systemSettingService.getSettingValueByKey("SettingTitle"));
        settings.setSettingLogo(systemSettingService.getSettingValueByKey("SettingLogo"));
        settings.setSettingEmail(systemSettingService.getSettingValueByKey("SettingEmail"));
        settings.setSettingHotline(systemSettingService.getSettingValueByKey("SettingHotline"));
        settings.setSettingAddress(systemSettingService.getSettingValueByKey("SettingAddress"));

        return ResponseEntity.ok(settings);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addSetting(@RequestBody SettingSystemDto req) {
        systemSettingService.saveOrUpdateSetting("SettingTitle", req.getSettingTitle());
        systemSettingService.saveOrUpdateSetting("SettingLogo", req.getSettingLogo());
        systemSettingService.saveOrUpdateSetting("SettingEmail", req.getSettingEmail());
        systemSettingService.saveOrUpdateSetting("SettingHotline", req.getSettingHotline());
        systemSettingService.saveOrUpdateSetting("SettingAddress", req.getSettingAddress());

        return ResponseEntity.ok("Setting added successfully");
    }
}

