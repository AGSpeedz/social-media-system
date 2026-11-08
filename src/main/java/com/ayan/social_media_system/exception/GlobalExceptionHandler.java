package com.ayan.social_media_system.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // HANDLE 404 NOT FOUND
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    // HANDLE VALIDATION ERRORS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request
        );

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        body.put("fieldErrors", fieldErrors);

        return ResponseEntity.badRequest().body(body);
    }

    // HANDLE INVALID BUSINESS RULES
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
    }

    // HANDLE INVALID JSON REQUESTS
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "Invalid request body",
                request
        );
    }

    // HANDLE DATABASE CONSTRAINT ERRORS
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.CONFLICT,
                "Database constraint violation",
                request
        );
    }

    // BUILD STANDARD ERROR RESPONSE
    private ResponseEntity<Map<String, Object>> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(status).body(
                createErrorBody(status, message, request)
        );
    }

    private Map<String, Object> createErrorBody(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getRequestURI());

        return body;
    }
}