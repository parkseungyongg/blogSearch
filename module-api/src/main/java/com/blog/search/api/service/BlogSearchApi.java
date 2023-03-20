package com.blog.search.api.service;


import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.exception.BlogSearchException;

public interface BlogSearchApi {
    /**
     블로그 검색 API를 호출하여 결과를 반환합니다.
     @param request 블로그 검색 요청 정보
     @return 블로그 검색 결과
     @throws BlogSearchException API 호출 중 예외가 발생한 경우
     */
    BlogSearchResult searchBlog(BlogSearchRequest request) throws BlogSearchException;
}
