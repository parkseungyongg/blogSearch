package com.blog.search.api.service;

import com.blog.search.api.dto.*;
import com.blog.search.core.service.SearchKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogSearchService {
    private final KakaoBlogSearchService kakaoBlogSearchService;
    private final SearchKeywordService searchKeywordService;

    public BlogSearchService(KakaoBlogSearchService kakaoBlogSearchService, SearchKeywordService searchKeywordService) {
        this.kakaoBlogSearchService = kakaoBlogSearchService;
        this.searchKeywordService = searchKeywordService;
    }

    public BlogSearchResult searchBlogs(BlogSearchRequestDto blogSearchRequestDto) {
        BlogSearchRequest request = BlogSearchRequest.builder()
                .query(blogSearchRequestDto.getQuery())
                .sort(blogSearchRequestDto.getSort())
                .page(blogSearchRequestDto.getPage())
                .size(blogSearchRequestDto.getSize())
                .build();

//        log.info("searchBlogs request={}", request.toString());

        KakaoBlogSearchResponse response = kakaoBlogSearchService.searchBlog(request);
        searchKeywordService.updateSearchKeyword(blogSearchRequestDto.getQuery());

        List<BlogSearchResponse> items = response.getDocuments().stream()
                .map(document -> BlogSearchResponse.builder()
                        .title(document.getTitle())
                        .contents(document.getContents())
                        .url(document.getUrl())
                        .blogname(document.getBlogname())
                        .datetime(document.getDatetime())
                        .thumbnail(document.getThumbnail())
                        .build()
                )
                .collect(Collectors.toList());

        BlogSearchResult blogSearchResult = BlogSearchResult.builder()
                .items(items)
                .page(blogSearchRequestDto.getPage())
                .size(blogSearchRequestDto.getSize())
                .totalElements(response.getMeta().getTotalCount())
                .totalPages(response.getMeta().getTotalCount() / blogSearchRequestDto.getSize())
                .sortType(blogSearchRequestDto.getSort())
                .build();

//        log.info("blogSearchResult={}", blogSearchResult.toString());

        return blogSearchResult;
    }
}
