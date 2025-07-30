package com.seek.client.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for REST controllers.
 * Provides centralized handling of common exceptions and
 * returns standardized HTTP responses with appropriate error messages.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link EntityNotFoundException} when an entity is not found.
     *
     * @param ex the thrown {@link EntityNotFoundException}
     * @return a response entity with a NOT_FOUND status and an error message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Entity not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles {@link MethodArgumentNotValidException} for validation errors on request payloads.
     *
     * @param ex the thrown {@link MethodArgumentNotValidException}
     * @return a response entity with an UNPROCESSABLE_ENTITY status and validation details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of(
                        "error", "Validation failed",
                        "details", errors
                ));
    }

    /**
     * Handles {@link ConstraintViolationException} for violations detected
     * during validation of method parameters or fields.
     *
     * @param ex the thrown {@link ConstraintViolationException}
     * @return a response entity with a BAD_REQUEST status and violation details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());

        List<String> violations = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Constraint violation", "details", violations ));
    }

    /**
     * Handles any unhandled {@link Exception}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Unexpected error occurred",
                "message", ex.getMessage()
                ));
    }

    /**
     * Handles {@link BadCredentialsException} when login credentials are invalid.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Unauthorized",
                "message", "Invalid username or password"
                ));
    }

    /**
     * Handles any {@link AuthenticationException} not specifically matched elsewhere.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.warn("Authentication error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Unauthorized",
                "message", ex.getMessage()
                ));
    }
}
