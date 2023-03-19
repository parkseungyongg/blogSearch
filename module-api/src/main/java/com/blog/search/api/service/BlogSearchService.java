package com.blog.search.api.service;

import com.blog.search.api.dto.*;
import com.blog.search.service.SearchKeywordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return BlogSearchResult.builder()
                .items(items)
                .page(blogSearchRequestDto.getPage())
                .size(blogSearchRequestDto.getSize())
                .totalElements(response.getMeta().getTotalCount())
                .totalPages(response.getMeta().getPageableCount())
                .sortType(blogSearchRequestDto.getSort())
                .build();
    }
}
