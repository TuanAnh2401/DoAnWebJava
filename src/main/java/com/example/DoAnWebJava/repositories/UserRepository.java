package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    User findByConfirmationToken(String confirmationToken);
    User findByUsername(String username);
    User findByEmail(String email);

}

