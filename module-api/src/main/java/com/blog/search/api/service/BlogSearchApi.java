package com.blog.search.api.service;


import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.exception.BlogSearchException;

public interface BlogSearchApi {
    BlogSearchResult searchBlog(BlogSearchRequest request) throws BlogSearchException;
}
