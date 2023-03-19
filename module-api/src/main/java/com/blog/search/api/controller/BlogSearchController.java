package com.blog.search.api.controller;

import com.blog.search.api.dto.BlogSearchRequestDto;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.model.BlogSortType;
import com.blog.search.api.service.BlogSearchService;
import com.blog.search.dto.SearchKeywordResponse;
import com.blog.search.service.SearchKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
            @Valid @ModelAttribute BlogSearchRequestDto blogSearchRequestDto,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            // 검증 실패 시 에러 응답 반환
            return ResponseEntity.badRequest().build();
        }
        BlogSearchResult blogSearchResult = blogSearchService.searchBlogs(blogSearchRequestDto);

        return ResponseEntity.ok(blogSearchResult);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SearchKeywordResponse>> getPopularKeywords() {
        List<SearchKeywordResponse> popularKeywords = searchKeywordService.getPopularKeywords();
        return ResponseEntity.ok(popularKeywords);
    }
}
