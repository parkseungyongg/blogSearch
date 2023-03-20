package com.blog.search.api.service;

import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchRequestDto;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.exception.ApiException;
import com.blog.search.core.service.SearchKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlogSearchService {
    private final SearchKeywordService searchKeywordService;
    private final List<BlogSearchApi> apis;

    public BlogSearchService(SearchKeywordService searchKeywordService, List<BlogSearchApi> apis) {
        this.searchKeywordService = searchKeywordService;
        this.apis = apis;
    }

    public BlogSearchResult searchBlogs(BlogSearchRequestDto blogSearchRequestDto) {
        BlogSearchRequest request = BlogSearchRequest.builder()
                .page(blogSearchRequestDto.getPage())
                .query(blogSearchRequestDto.getQuery())
                .size(blogSearchRequestDto.getSize())
                .sort(blogSearchRequestDto.getSort())
                .build();

        BlogSearchResult result = null;

        for(BlogSearchApi api : apis) {
            try {
                result = api.searchBlog(request);
                searchKeywordService.updateSearchKeyword(blogSearchRequestDto.getQuery());
                break;
            } catch(Exception e) {
                // API 호출에 실패한 경우 다음 API를 호출합니다.
                log.info("호출 실패: {}", api.getClass(), e.getStackTrace());
            }
        }

        if(result == null) {
            throw new ApiException("API 호출중 에러가 발생했습니다. 잠시후 다시 시도해주세요.");
        }

        return result;
    }
}
