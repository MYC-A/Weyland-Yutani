package com.REST_API.bishop_prototype.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String message;
    private String code;
    private LocalDateTime timestamp;
}