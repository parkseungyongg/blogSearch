package com.blog.search.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String message, HttpStatus status, String errorCode) {
        this.message = message;
        this.status = status.value();
        this.errorCode = errorCode;
    }
}
