package com.smartclinic.clinic.controller;

import com.smartclinic.clinic.dto.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Test/Health Controller
 * Provides endpoints for testing authentication and role-based access
 */
@Slf4j
@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HealthCheckController {

    /**
     * Health check endpoint - accessible without authentication
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> health() {
        log.info("Health check endpoint called");

        Map<String, String> data = new HashMap<>();
        data.put("status", "UP");
        data.put("message", "Server is running successfully");
        data.put("timestamp", String.valueOf(System.currentTimeMillis()));

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Server is healthy",
                data,
                HttpStatus.OK.value()
        ));
    }

    /**
     * Get current authenticated user info
     * Requires authentication
     * @return current user information
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> getCurrentUser() {
        log.info("Get current user endpoint called");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, String> data = new HashMap<>();
        data.put("username", authentication.getName());
        data.put("authorities", authentication.getAuthorities().toString());
        data.put("authenticated", String.valueOf(authentication.isAuthenticated()));

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Current user information",
                data,
                HttpStatus.OK.value()
        ));
    }

    /**
     * Test endpoint accessible only by admins
     * @return admin test response
     */
    @GetMapping("/admin-test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> adminTest() {
        log.info("Admin test endpoint called");

        Map<String, String> data = new HashMap<>();
        data.put("message", "This is an admin-only endpoint");
        data.put("role", "ADMIN");

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Access granted to admin",
                data,
                HttpStatus.OK.value()
        ));
    }

    /**
     * Test endpoint accessible only by doctors
     * @return doctor test response
     */
    @GetMapping("/doctor-test")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> doctorTest() {
        log.info("Doctor test endpoint called");

        Map<String, String> data = new HashMap<>();
        data.put("message", "This is a doctor-only endpoint");
        data.put("role", "DOCTOR");

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Access granted to doctor",
                data,
                HttpStatus.OK.value()
        ));
    }

    /**
     * Test endpoint accessible only by patients
     * @return patient test response
     */
    @GetMapping("/patient-test")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> patientTest() {
        log.info("Patient test endpoint called");

        Map<String, String> data = new HashMap<>();
        data.put("message", "This is a patient-only endpoint");
        data.put("role", "PATIENT");

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Access granted to patient",
                data,
                HttpStatus.OK.value()
        ));
    }
}

