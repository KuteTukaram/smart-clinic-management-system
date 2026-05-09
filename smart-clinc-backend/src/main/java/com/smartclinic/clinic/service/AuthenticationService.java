package com.smartclinic.clinic.service;

import com.smartclinic.clinic.dto.AuthResponseDTO;
import com.smartclinic.clinic.dto.LoginRequestDTO;
import com.smartclinic.clinic.dto.RegisterRequestDTO;
import com.smartclinic.clinic.dto.UserResponseDTO;
import com.smartclinic.clinic.entity.User;
import com.smartclinic.clinic.exception.DuplicateResourceException;
import com.smartclinic.clinic.exception.ValidationException;
import com.smartclinic.clinic.repository.UserRepository;
import com.smartclinic.clinic.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authentication Service
 * Handles user registration and login logic
 */
@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Register a new user
     * @param registerRequest the registration request DTO
     * @return AuthResponseDTO with JWT token and user info
     */
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        log.info("Attempting to register new user with email: {}", registerRequest.getEmail());

        // Validate input
        validateRegistrationInput(registerRequest);

        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("Registration failed: Email {} already exists", registerRequest.getEmail());
            throw new DuplicateResourceException("Email already registered. Please try another email.");
        }

        // Create new user
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        user.setIsActive(true);

        // Save user
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with email: {}", savedUser.getEmail());

        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser);

        // Build response
        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .user(convertUserToDTO(savedUser))
                .message("User registered successfully")
                .build();
    }

    /**
     * Login user
     * @param loginRequest the login request DTO
     * @return AuthResponseDTO with JWT token and user info
     */
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Attempting login for email: {}", loginRequest.getEmail());

        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Get authenticated user
            User user = (User) authentication.getPrincipal();
            log.info("User logged in successfully: {}", user.getEmail());

            // Generate JWT token
            String token = jwtUtil.generateToken(user);

            // Build response
            return AuthResponseDTO.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .user(convertUserToDTO(user))
                    .message("Login successful")
                    .build();

        } catch (BadCredentialsException e) {
            log.warn("Login failed for email {}: Invalid credentials", loginRequest.getEmail());
            throw new ValidationException("Invalid email or password");
        } catch (Exception e) {
            log.error("Login error for email {}: {}", loginRequest.getEmail(), e.getMessage());
            throw new ValidationException("Login failed. Please try again.");
        }
    }

    /**
     * Validate registration input
     * @param registerRequest the registration request DTO
     */
    private void validateRegistrationInput(RegisterRequestDTO registerRequest) {
        // Check if passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }

        // Additional validation can be added here
        if (registerRequest.getFullName() == null || registerRequest.getFullName().trim().isEmpty()) {
            throw new ValidationException("Full name is required");
        }

        if (registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        if (registerRequest.getPhone() == null || registerRequest.getPhone().trim().isEmpty()) {
            throw new ValidationException("Phone is required");
        }
    }

    /**
     * Convert User entity to UserResponseDTO
     * @param user the user entity
     * @return UserResponseDTO
     */
    private UserResponseDTO convertUserToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

