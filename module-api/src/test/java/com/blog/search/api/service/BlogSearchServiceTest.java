package com.blog.search.api.service;

import com.blog.search.SearchBlogApiApplication;
import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchRequestDto;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.exception.BlogSearchException;
import com.blog.search.api.exception.KakaoApiException;
import com.blog.search.api.exception.NaverApiException;
import com.blog.search.core.entity.SearchKeyword;
import com.blog.search.core.repository.SearchKeywordRepository;
import com.blog.search.core.service.SearchKeywordService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchBlogApiApplication.class)
class BlogSearchServiceTest {

    @Autowired
    SearchKeywordService searchKeywordService;

    @Autowired
    SearchKeywordRepository searchKeywordRepository;

    @Mock
    KakaoBlogSearchApi kakaoBlogSearchApi;

    @Mock
    NaverBlogSearchApi naverBlogSearchApi;

    @Before
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() {
        searchKeywordRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 Blog Search Api에서 검색 실패시 BlogSearchException이 발생한다.")
    void searchBlogs_allApiFailed_shouldThrowApiException() {
        // given
        when(kakaoBlogSearchApi.searchBlog(any(BlogSearchRequest.class))).thenThrow(new KakaoApiException("kakao api error"));
        when(naverBlogSearchApi.searchBlog(any(BlogSearchRequest.class))).thenThrow(new NaverApiException("naver api error"));

        BlogSearchService blogSearchService = new BlogSearchService(searchKeywordService
                , Arrays.asList(kakaoBlogSearchApi, naverBlogSearchApi));

        BlogSearchRequestDto blogSearchRequestDto = new BlogSearchRequestDto();
        blogSearchRequestDto.setQuery("스프링");

        // when, then
        assertThatThrownBy(() -> blogSearchService.searchBlogs(blogSearchRequestDto))
                .isInstanceOf(BlogSearchException.class)
                .hasMessage("API 호출중 에러가 발생했습니다. 잠시후 다시 시도해주세요.");
    }

    @Test
    @DisplayName("만약 api가 정상적으로 호출되는 경우 인기검색어 키워드에 저장된다.")
    void searchBlogs_WhenRequestIsSuccessful_ShouldSaveSearchKeyword() {
        // given
        when(kakaoBlogSearchApi.searchBlog(any(BlogSearchRequest.class))).then(invocation -> BlogSearchResult.builder().build());
        when(naverBlogSearchApi.searchBlog(any(BlogSearchRequest.class))).then(invocation -> BlogSearchResult.builder().build());

        BlogSearchService blogSearchService = new BlogSearchService(searchKeywordService
                , Arrays.asList(kakaoBlogSearchApi, naverBlogSearchApi));

        BlogSearchRequestDto blogSearchRequestDto = new BlogSearchRequestDto();
        String keyword = "스프링";
        blogSearchRequestDto.setQuery(keyword);

        // when
        blogSearchService.searchBlogs(blogSearchRequestDto);

        // then
        SearchKeyword expect = searchKeywordRepository.findByKeyword(keyword);

        assertThat(expect.getKeyword()).isEqualTo(keyword);
        assertThat(expect.getCount()).isEqualTo(1L);
    }

    @Test
    @DisplayName("만약 api가 정상적으로 호출되지 않은 경우 인기검색어 키워드에 저장되지 않아야 한다.")
    void searchBlogs_WhenRequestFails_ShouldNotSaveSearchKeyword() {
        // given
        when(kakaoBlogSearchApi.searchBlog(any(BlogSearchRequest.class))).thenThrow(new KakaoApiException("kakao api error"));
        when(naverBlogSearchApi.searchBlog(any(BlogSearchRequest.class))).thenThrow(new NaverApiException("naver api error"));

        BlogSearchService blogSearchService = new BlogSearchService(searchKeywordService
                , Arrays.asList(kakaoBlogSearchApi, naverBlogSearchApi));

        BlogSearchRequestDto blogSearchRequestDto = new BlogSearchRequestDto();
        String keyword = "스프링 부트";
        blogSearchRequestDto.setQuery(keyword);

        // when
        assertThatThrownBy(() -> blogSearchService.searchBlogs(blogSearchRequestDto));

        // then
        SearchKeyword expect = searchKeywordRepository.findByKeyword(keyword);
        assertThat(expect).isNull();
    }

}