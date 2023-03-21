package com.blog.search.api.controller;

import com.blog.search.api.dto.BlogSearchRequestDto;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.service.BlogSearchService;
import com.blog.search.core.dto.PopularKeywords;
import com.blog.search.core.service.SearchKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/search/blog")
@Slf4j
public class BlogSearchController {
    private final BlogSearchService blogSearchService;
    private final SearchKeywordService searchKeywordService;

    public BlogSearchController(BlogSearchService blogSearchService, SearchKeywordService searchKeywordService) {
        this.blogSearchService = blogSearchService;
        this.searchKeywordService = searchKeywordService;
    }

    /**
     * Blog 검색 API 호출 및 결과를 반환한다.
     *
     * @param blogSearchRequestDto Blog 검색 요청 DTO 객체
     * @return Blog 검색 결과
     */
    @GetMapping("")
    public ResponseEntity<BlogSearchResult> searchBlog(
            @Valid @ModelAttribute BlogSearchRequestDto blogSearchRequestDto) {
        BlogSearchResult blogSearchResult = blogSearchService.searchBlogs(blogSearchRequestDto);
        return ResponseEntity.ok(blogSearchResult);
    }

    /**
     * 인기 검색어 목록을 조회한다.
     *
     * @return 인기 검색어 목록
     */
    @GetMapping("/popular")
    public ResponseEntity<PopularKeywords> getPopularKeywords() {
        PopularKeywords popularKeywords = searchKeywordService.getPopularKeywords();

        return ResponseEntity.ok(popularKeywords);
    }
}
