package com.blog.search.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SearchKeywordResponse {
    private String keyword;
    private Long count;
}
