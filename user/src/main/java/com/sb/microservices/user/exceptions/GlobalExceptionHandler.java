package com.sb.microservices.user.exceptions;

import com.sb.microservices.user.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> HandlerResourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponseBuilder  = ApiResponse.builder()
                .message(message)
                .success(true)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<ApiResponse>(apiResponseBuilder,HttpStatus.NOT_FOUND);
    }
}
