package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, String> {
}

