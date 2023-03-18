package com.blog.search.service;

import com.blog.search.dto.BlogSearchResponse;
import com.blog.search.dto.SearchBlogRequest;
import com.blog.search.dto.SearchBlogResponse;
import com.blog.search.dto.SearchResult;
import com.blog.search.model.BlogSortType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogSearchService {
    private final KakaoSearchService kakaoSearchService;

    public BlogSearchService(KakaoSearchService kakaoSearchService) {
        this.kakaoSearchService = kakaoSearchService;
    }

    public SearchResult searchBlogs(String query, BlogSortType sortType, int page, int size) {
        SearchBlogRequest request = SearchBlogRequest.builder()
                .query(query)
                .sort(sortType)
                .page(page)
                .size(size)
                .build();

        SearchBlogResponse response = kakaoSearchService.searchBlog(request);
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

        return SearchResult.builder()
                .items(items)
                .page(page)
                .size(size)
                .totalElements(response.getMeta().getTotalCount())
                .totalPages(response.getMeta().getTotalCount())
                .build();
    }
}
