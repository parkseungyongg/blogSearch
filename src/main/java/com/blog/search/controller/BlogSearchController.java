package com.blog.search.controller;

import com.blog.search.dto.SearchResult;
import com.blog.search.model.BlogSortType;
import com.blog.search.service.BlogSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class BlogSearchController {
    private final BlogSearchService blogSearchService;

    public BlogSearchController(BlogSearchService blogSearchService) {
        this.blogSearchService = blogSearchService;
    }

    @GetMapping("")
    public ResponseEntity<SearchResult> searchBlog(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "ACCURACY") BlogSortType sort,
            @RequestParam(required = false, defaultValue = "1") Integer page) {
        SearchResult result = blogSearchService.searchBlogs(query, sort, page, 10);
        return ResponseEntity.ok(result);
    }
}
