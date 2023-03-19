package com.blog.search.api.exception;

public class NaverApiException extends RuntimeException {
    public NaverApiException(String message) {
        super(message);
    }

    public NaverApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
