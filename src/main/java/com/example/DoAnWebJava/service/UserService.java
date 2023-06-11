package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.UserDto;
import com.example.DoAnWebJava.entities.User;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.repositories.UserRepository;
import com.example.DoAnWebJava.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${app.confirmation-url}")
    private String confirmationUrl;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.jwtTokenUtil = jwtTokenUtil;
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

        // Send confirmation email to the user
        sendConfirmationEmail(user.getEmail(), confirmationToken);
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


    private void sendConfirmationEmail(String email, String confirmationToken) {
        // Compose the confirmation email
        String subject = "Confirm Your Registration";
        String body = "Please confirm your registration by clicking the link below:\n\n"
                + confirmationUrl + "?token=" + confirmationToken;

        // Create a new email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        // Send the email
        javaMailSender.send(message);
    }
}


