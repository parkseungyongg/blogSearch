package com.blog.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 제공자를 구분하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum ProviderType {
    NAVER("query", "sort", "display", "start"),
    KAKAO("query", "sort", "size", "page");

    private final String queryKey;
    private final String sortKey;
    private final String sizeKey;
    private final String pageKey;

    /**
     * 블로그 검색 결과를 정렬하는 값을 반환합니다.
     * @param blogSortType 블로그 검색 결과 정렬 방법
     * @return 블로그 검색 결과를 정렬하는 값
     * @throws IllegalArgumentException 지원하지 않는 API 제공자인 경우 발생합니다.
     */
    public String getSortValue(BlogSortType blogSortType) {
        if (this == NAVER) {
            if (blogSortType == BlogSortType.ACCURACY) {
                return "sim";
            } else if (blogSortType == BlogSortType.RECENCY) {
                return "date";
            }
        } else if (this == KAKAO) {
            return blogSortType.getValue();
        }

        throw new IllegalArgumentException("Unsupported ProviderType: " + this);
    }
}
