package com.example.DoAnWebJava.repositories;

import com.example.DoAnWebJava.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    User findByConfirmationToken(String confirmationToken);
    User findByUsername(String username);
    User findByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_user_role (user_id, role_id) VALUES (?1, ?2)", nativeQuery = true)
    void addRoleToUser(Long userId, Long roleId);

    int countByUsernameContainingIgnoreCase(String searchString);

}

