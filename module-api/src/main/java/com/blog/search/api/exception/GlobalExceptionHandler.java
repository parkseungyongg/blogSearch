package com.blog.search.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogSearchException.class)
    public ResponseEntity<ErrorResponse> handleBlogSearchException(BlogSearchException e) {
        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KakaoApiException.class)
    public ResponseEntity<ErrorResponse> handleKakaoApiException(KakaoApiException e) {
        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NaverApiException.class)
    public ResponseEntity<ErrorResponse> handleNaverApiException(NaverApiException e) {
        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
