package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.UserDto;
import com.example.DoAnWebJava.entities.User;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.repositories.UserRepository;
import com.example.DoAnWebJava.util.JwtTokenUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.UUID;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${app.confirmation-url}")
    private String confirmationUrl;
    @Value("${app.reset-password-url}")
    private String resetPasswordUrl;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, JwtTokenUtil jwtTokenUtil, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.jwtTokenUtil = jwtTokenUtil;
        this.templateEngine = templateEngine;
    }

    public void registerUser(UserDto userDto) throws UserRegistrationException {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserRegistrationException("Username is already taken");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserRegistrationException("Email is already registered");
        }
        if (userRepository.existsByPhone(userDto.getPhone())) {
            throw new UserRegistrationException("Phone is already registered");
        }

        // Generate a confirmation token
        String confirmationToken = UUID.randomUUID().toString();

        // Create a new user entity with the encoded password
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .confirmed(false)
                .confirmationToken(confirmationToken)
                .build();

        // Save the user in the repository
        userRepository.save(user);

        // Gửi email xác nhận đăng ký cho user
        String subject = "Confirm Your Registration";
        String greeting = "Welcome, " + userDto.getUsername();
        String message = "Please confirm your registration by clicking the link below:";
        sendConfirmationEmail(user.getEmail(), subject, greeting, message, confirmationToken);
    }

    public void confirmUser(String confirmationToken) throws UserRegistrationException {
        User user = userRepository.findByConfirmationToken(confirmationToken);
        if (user == null) {
            throw new UserRegistrationException("Invalid confirmation token");
        }

        // Set the confirmed flag to true
        user.setConfirmed(true);
        user.setConfirmationToken(null);
        userRepository.save(user);
    }

    public String authenticate(String username, String password) throws UserRegistrationException {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new UserRegistrationException("Invalid credentials");
        }

        if (!user.isConfirmed()) {
            throw new UserRegistrationException("Account not confirmed");
        }
        // Generate and return the access token
        String accessToken = jwtTokenUtil.generateToken(user);
        return accessToken;
    }

    public void sendPasswordResetEmail(String email) throws UserRegistrationException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserRegistrationException("User not found");
        }

        if (!user.isConfirmed()) {
            throw new UserRegistrationException("User not confirmed");
        }

        // Generate a password reset token
        String resetToken = UUID.randomUUID().toString();

        // Update the user's reset token in the database
        user.setConfirmationToken(resetToken);
        userRepository.save(user);

        // Gửi email xác nhận đăng ký cho user
        String subject = "Reset Your Password";
        String greeting = "Welcome, " + user.getUsername();
        String message = "Please reset your password by clicking the link below:";
        sendConfirmationEmail(user.getEmail(), subject, greeting, message, resetToken);
    }

    private void sendConfirmationEmail(String email, String subject, String greeting, String message, String confirmationToken) throws UserRegistrationException {
        Context context = new Context();
        context.setVariable("title", subject);
        context.setVariable("greeting", greeting);
        context.setVariable("message", message);
        context.setVariable("resetPasswordUrl", resetPasswordUrl + "?token=" + confirmationToken);

        String body = templateEngine.process("user/sendMail", context);

        // Tạo email mới
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new UserRegistrationException("Failed to send confirmation email");
        }
    }

    public void resetPassword(String resetToken, String newPassword) throws UserRegistrationException {
        User user = userRepository.findByConfirmationToken(resetToken);
        if (user == null) {
            throw new UserRegistrationException("Invalid reset token");
        }

        // Set the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setConfirmationToken(null);
        userRepository.save(user);
    }
}


