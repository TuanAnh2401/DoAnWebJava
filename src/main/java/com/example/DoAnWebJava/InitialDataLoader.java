package com.example.DoAnWebJava;

import com.example.DoAnWebJava.entities.Role;
import com.example.DoAnWebJava.repositories.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public InitialDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Kiểm tra xem đã có vai trò trong cơ sở dữ liệu chưa
        if (roleRepository.count() == 0) {
            // Tạo vai trò Admin
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Administrator role");
            roleRepository.save(adminRole);

            // Tạo vai trò User
            Role userRole = new Role();
            userRole.setName("USER");
            userRole.setDescription("User role");
            roleRepository.save(userRole);
        }
    }
}

