package com.blog.search.api.exception;

/**
 * Blog 검색 API 요청/응답 중 예외가 발생할 경우 발생시킬 예외 클래스
 */
public class BlogSearchException extends RuntimeException {
    public BlogSearchException(String message) {
        super(message);
    }

    public BlogSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
