package com.antonio.blog.exception;

import com.antonio.blog.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage(), false),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(
                new ApiResponse(message, false),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleSqlException(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(
                new ApiResponse(message, false),
                HttpStatus.BAD_REQUEST
        );
    }

//    @ExceptionHandler(FileUploadException.class)
//    public ResponseEntity<ApiResponse> handleFileUploadException(FileUploadException ex) {
//        return new ResponseEntity<>(
//                new ApiResponse(ex.getMessage(), false),
//                HttpStatus.BAD_REQUEST
//        );
//    }
}
