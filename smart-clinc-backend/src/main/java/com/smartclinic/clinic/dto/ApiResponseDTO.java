package com.smartclinic.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API Response wrapper for consistent response format
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {

    private boolean success;

    private String message;

    private T data;

    private int statusCode;

    public static <T> ApiResponseDTO<T> success(String message, T data, int statusCode) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .statusCode(statusCode)
                .build();
    }

    public static <T> ApiResponseDTO<T> error(String message, int statusCode) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .statusCode(statusCode)
                .build();
    }
}

