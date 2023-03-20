package com.blog.search.api.exception;

/**
 * 카카오 API 호출 중 예외가 발생한 경우에 사용되는 예외 클래스
 */
public class KakaoApiException extends RuntimeException {
    public KakaoApiException(String message) {
        super(message);
    }

    public KakaoApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
