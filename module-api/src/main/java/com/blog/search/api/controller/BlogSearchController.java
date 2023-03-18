package com.blog.search.api.controller;

import com.blog.search.dto.SearchKeywordResponse;
import com.blog.search.api.dto.SearchResult;
import com.blog.search.api.model.BlogSortType;
import com.blog.search.api.service.BlogSearchService;
import com.blog.search.service.SearchKeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class BlogSearchController {
    private final BlogSearchService blogSearchService;
    private final SearchKeywordService searchKeywordService;

    public BlogSearchController(BlogSearchService blogSearchService, SearchKeywordService searchKeywordService) {
        this.blogSearchService = blogSearchService;
        this.searchKeywordService = searchKeywordService;
    }

    @GetMapping("")
    public ResponseEntity<SearchResult> searchBlog(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "ACCURACY") BlogSortType sort,
            @RequestParam(required = false, defaultValue = "1") Integer page) {
        SearchResult result = blogSearchService.searchBlogs(query, sort, page, 10);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SearchKeywordResponse>> getPopularKeywords() {
        List<SearchKeywordResponse> popularKeywords = searchKeywordService.getPopularKeywords();
        return ResponseEntity.ok(popularKeywords);
    }
}
