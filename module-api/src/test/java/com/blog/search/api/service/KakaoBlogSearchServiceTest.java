package com.blog.search.api.service;

import com.blog.search.SearchBlogApiApplication;
import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchBlogApiApplication.class)
class KakaoBlogSearchServiceTest {
    @Autowired
    KakaoBlogSearchApi kakaoBlogSearchApi;

    @Test
    @DisplayName("KakaoBlogSearchApi 검색 요청 시 정상적으로 결과를 반환한다.")
    void searchBlogTest() throws Exception {
        // given
        String query = "스프링";

        BlogSearchRequest request = BlogSearchRequest.builder()
                .query(query)
                .page(1)
                .size(10)
                .build();

        //when
        BlogSearchResult blogSearchResult = kakaoBlogSearchApi.searchBlog(request);

        //then
        assertThat(blogSearchResult.getItems()).isNotEmpty();
        assertThat(blogSearchResult.getItems().get(0).getContents()).contains(query);
    }
}