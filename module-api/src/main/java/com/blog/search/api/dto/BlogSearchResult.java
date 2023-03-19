package com.blog.search.api.dto;

import com.blog.search.api.model.BlogSortType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class BlogSearchResult {
    private List<BlogSearchResponse> items;

    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private BlogSortType sortType;
}
