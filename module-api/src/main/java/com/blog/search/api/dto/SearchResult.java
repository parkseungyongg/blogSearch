package com.blog.search.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchResult {
    private List<BlogSearchResponse> items;

    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
}
