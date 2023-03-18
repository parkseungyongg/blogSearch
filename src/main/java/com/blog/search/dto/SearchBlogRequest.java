package com.blog.search.dto;

import com.blog.search.model.BlogSortType;
import lombok.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchBlogRequest {
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
