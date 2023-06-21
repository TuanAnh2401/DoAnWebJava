package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.LoginDto;
import com.example.DoAnWebJava.dto.LoginResponseDto;
import com.example.DoAnWebJava.dto.UpdateDto;
import com.example.DoAnWebJava.dto.UserDto;
import com.example.DoAnWebJava.entities.Coupon;
import com.example.DoAnWebJava.entities.User;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.UserService;
import com.example.DoAnWebJava.support.ResponsePaging;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/paginate")
    public ResponseEntity<ResponsePaging<List<User>>> getPaginatedUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchString
    ) {
        int pageSize = 10; // Kích thước trang (số lượng liên hệ trên mỗi trang)
        int totalUsers = userService.getTotalUsers(searchString);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        // Giới hạn số trang hiện tại trong khoảng từ 1 đến tổng số trang
        page = Math.max(1, Math.min(page, totalPages));

        // Lấy danh sách liên hệ phân trang từ Service
        List<User> users = userService.getPaginatedContacts(page, pageSize, searchString);

        // Tạo đối tượng ResponsePaging để chứa thông tin phân trang và danh sách liên hệ
        ResponsePaging<List<User>> responsePaging = new ResponsePaging<>(users, totalPages, page, totalUsers);

        return ResponseEntity.ok(responsePaging);
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
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            userService.sendPasswordResetEmail(email);
            return ResponseEntity.ok().body("Password reset email sent");
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestParams) {
        String resetToken = requestParams.get("token");
        String newPassword = requestParams.get("password");
        try {
            userService.resetPassword(resetToken, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UpdateDto updateDto) {
        try {
            User updatedUser = userService.updateUser(username, updateDto);
            if (updatedUser != null) {
                UserDto updatedUserDto = new UserDto();
                updatedUserDto.setUsername(updatedUser.getUsername());
                updatedUserDto.setName(updatedUser.getName());
                updatedUserDto.setAvt(updatedUser.getAvt());
                updatedUserDto.setPassword(updatedUser.getPassword());
                updatedUserDto.setEmail(updatedUser.getEmail());
                updatedUserDto.setPhone(updatedUser.getPhone());
                updatedUserDto.setAddress(updatedUser.getAddress());

                return ResponseEntity.ok(updatedUserDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}


