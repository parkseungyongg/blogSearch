package com.blog.search.api.dto;

import com.blog.search.api.model.BlogSortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BlogSearchResult {
    private List<BlogSearchResponse> items;

    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private BlogSortType sortType;
}
