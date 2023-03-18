package com.blog.search.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
public class SearchBlogRequest {
    private final String query;
    private final Integer page;
    private final Integer size;
    private final SortType sort;

    public SearchBlogRequest(String query, Integer page, Integer size, SortType sort) {
        this.query = query;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public MultiValueMap<String, String> toParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);

        if(sort != null) {
            params.add("sort", sort.getValue());
        }
        if(size != null) {
            params.add("size", String.valueOf(size));
        }
        if(page != null) {
            params.add("page", String.valueOf(page));
        }

        return params;
    }

    public enum SortType {
        ACCURACY("accuracy"), LATEST("recency");

        private final String value;

        SortType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
