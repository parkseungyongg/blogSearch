package com.blog.search.api.dto;

import com.blog.search.api.model.BlogSortType;
import com.blog.search.api.model.ProviderType;
import lombok.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class BlogSearchRequest {
    private final String query;
    private final Integer page;
    private final Integer size;
    private final BlogSortType sort;

    public MultiValueMap<String, String> toParams(ProviderType provider) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(provider.getQueryKey(), query);

        if (sort != null) {
            params.add(provider.getSortKey(), provider.getSortValue(sort));
        }
        if (size != null) {
            params.add(provider.getSizeKey(), String.valueOf(size));
        }
        if (page != null) {
            params.add(provider.getPageKey(), String.valueOf(page));
        }
        return params;
    }
}
