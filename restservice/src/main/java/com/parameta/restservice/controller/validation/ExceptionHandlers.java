package com.parameta.restservice.controller.validation;

import com.parameta.restservice.util.exception.DataNotFoundException;
import com.parameta.restservice.util.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errorMap = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField, // error index
                        f -> f.getDefaultMessage() == null ? "" : f.getDefaultMessage()                  // error message
                ));

        var response = ErrorDTO.builder()
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(errorMap)
                .build();

        return ResponseEntity.badRequest().body(response);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorDTO> handleValidationException(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> errorMap = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString().split("\\.")[1], // parameter path
                        ConstraintViolation::getMessage                  // error message
                ));

        var response = ErrorDTO.builder()
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(errorMap)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ErrorDTO> handleDataNotFoundException(DataNotFoundException ex, HttpServletRequest request) {
        var response = ErrorDTO.builder()
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND)
                .code(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Map.of("message", ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ValidationException.class, MissingServletRequestParameterException.class})
    public final ResponseEntity<ErrorDTO> handleValidationException(Exception ex, HttpServletRequest request) {
        var response = ErrorDTO.builder()
                .path(request.getRequestURI())
                .status(HttpStatus.CONFLICT)
                .code(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .errors(Map.of("message", ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
