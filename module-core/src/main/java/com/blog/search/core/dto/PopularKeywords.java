package com.blog.search.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PopularKeywords {
    List<SearchKeywordResponse> popularKeywords;
}
