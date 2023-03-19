package com.blog.search.api.controller;

import lombok.extern.slf4j.Slf4j;

import com.blog.search.api.dto.BlogSearchRequestDto;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.service.BlogSearchService;
import com.blog.search.core.dto.SearchKeywordResponse;
import com.blog.search.core.service.SearchKeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@Slf4j
public class BlogSearchController {
    private final BlogSearchService blogSearchService;
    private final SearchKeywordService searchKeywordService;

    public BlogSearchController(BlogSearchService blogSearchService, SearchKeywordService searchKeywordService) {
        this.blogSearchService = blogSearchService;
        this.searchKeywordService = searchKeywordService;
    }

    @GetMapping("")
    public ResponseEntity<BlogSearchResult> searchBlog(
            @Valid @ModelAttribute BlogSearchRequestDto blogSearchRequestDto) {
        BlogSearchResult blogSearchResult = blogSearchService.searchBlogs(blogSearchRequestDto);
        return ResponseEntity.ok(blogSearchResult);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SearchKeywordResponse>> getPopularKeywords() {
        List<SearchKeywordResponse> popularKeywords = searchKeywordService.getPopularKeywords();

        return ResponseEntity.ok(popularKeywords);
    }
}
