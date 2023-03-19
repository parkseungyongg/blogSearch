package com.blog.search.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String errorCode;
}
