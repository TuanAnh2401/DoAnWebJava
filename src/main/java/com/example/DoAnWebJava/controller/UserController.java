package com.example.DoAnWebJava.controller;

import com.example.DoAnWebJava.dto.LoginDto;
import com.example.DoAnWebJava.dto.LoginResponseDto;
import com.example.DoAnWebJava.dto.UserDto;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String confirmationToken) {
        try {
            userService.confirmUser(confirmationToken);
            return ResponseEntity.ok().body("User confirmed successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginRequest) {
        try {
            String accessToken = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (accessToken != null) {
                LoginResponseDto responseDto = new LoginResponseDto(accessToken);
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(null));
            }
        } catch (UserRegistrationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(null));
        }
    }
}


