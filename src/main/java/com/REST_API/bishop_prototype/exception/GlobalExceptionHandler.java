package com.REST_API.bishop_prototype.exception;


import com.REST_API.bishop_prototype.dto.ErrorResponseDTO;
import com.weyland.starter.core.exception.CommandServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommandServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleTaskQueueException(CommandServiceException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), "QUEUE_FULL", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), "INTERNAL_SERVER_ERROR", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}