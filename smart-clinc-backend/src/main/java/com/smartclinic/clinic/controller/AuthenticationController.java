package com.smartclinic.clinic.controller;

import com.smartclinic.clinic.dto.ApiResponseDTO;
import com.smartclinic.clinic.dto.AuthResponseDTO;
import com.smartclinic.clinic.dto.LoginRequestDTO;
import com.smartclinic.clinic.dto.RegisterRequestDTO;
import com.smartclinic.clinic.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles user registration and login endpoints
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Register a new user
     * @param registerRequest the registration request
     * @return ApiResponseDTO with authentication response
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO registerRequest) {
        log.info("Register endpoint called for email: {}", registerRequest.getEmail());

        try {
            AuthResponseDTO response = authenticationService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(
                            "User registered successfully",
                            response,
                            HttpStatus.CREATED.value()
                    ));
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Login user
     * @param loginRequest the login request
     * @return ApiResponseDTO with authentication response
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Login endpoint called for email: {}", loginRequest.getEmail());

        try {
            AuthResponseDTO response = authenticationService.login(loginRequest);
            return ResponseEntity.ok()
                    .body(ApiResponseDTO.success(
                            "Login successful",
                            response,
                            HttpStatus.OK.value()
                    ));
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            throw e;
        }
    }
}

