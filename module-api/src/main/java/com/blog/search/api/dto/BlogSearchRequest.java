package com.blog.search.api.dto;

import com.blog.search.api.model.BlogSortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BlogSearchRequest {
    private final String query;
    private final Integer page;
    private final Integer size;
    private final BlogSortType sort;

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


}
