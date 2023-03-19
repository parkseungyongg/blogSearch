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
