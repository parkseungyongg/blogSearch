package com.blog.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProviderType {
    NAVER("query", "sort", "display", "start"),
    KAKAO("query", "sort", "size", "page");

    private final String queryKey;
    private final String sortKey;
    private final String sizeKey;
    private final String pageKey;

}
