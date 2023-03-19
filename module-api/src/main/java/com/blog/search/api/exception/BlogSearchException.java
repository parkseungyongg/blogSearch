package com.blog.search.api.exception;

public class BlogSearchException extends RuntimeException {
    public BlogSearchException(String message) {
        super(message);
    }

    public BlogSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
