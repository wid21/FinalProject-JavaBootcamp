package com.example.finalproject.ControllerAdvice;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.ApiResponse.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
        @ExceptionHandler(value = ApiException.class)
        public ResponseEntity<ApiResponse> ApiException(ApiException e) {
            String message = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ApiResponse> DataIntegrityViolationException(DataIntegrityViolationException e) {
            String message = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiResponse> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e1) {
            String message = e1.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse> MethodArgumentNotValidException(MethodArgumentNotValidException e2) {
            String message = e2.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));

        }
        @ExceptionHandler(MissingPathVariableException.class)
        public ResponseEntity<ApiResponse> MissingPathVariableException(MissingPathVariableException e3) {
            String message = e3.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
        public ResponseEntity<ApiResponse> InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e4) {
            String message = e4.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiResponse> ConstraintViolationException(ConstraintViolationException e5) {
            String message = e5.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }


}
