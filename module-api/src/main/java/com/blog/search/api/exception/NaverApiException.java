package com.blog.search.api.exception;

/**
 * 네이버 API 호출 중 예외가 발생한 경우에 사용되는 예외 클래스
 */
public class NaverApiException extends RuntimeException {
    public NaverApiException(String message) {
        super(message);
    }

    public NaverApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
