package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.SystemSetting;
import com.example.DoAnWebJava.repositories.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemSettingService {

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    public void saveOrUpdateSetting(String key, String value) {
        SystemSetting setting = systemSettingRepository.findById(key).orElse(new SystemSetting());
        setting.setSettingKey(key);
        setting.setSettingValue(value);
        systemSettingRepository.save(setting);
    }
    public String getSettingValueByKey(String key) {
        Optional<SystemSetting> settingOptional = systemSettingRepository.findById(key);
        return settingOptional.map(SystemSetting::getSettingValue).orElse(null);
    }

}

