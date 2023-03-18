package com.blog.search.api.service;

import com.blog.search.api.dto.SearchBlogRequest;
import com.blog.search.api.dto.SearchBlogResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
class KakaoSearchServiceTest {
    @Autowired
    KakaoSearchService kakaoSearchService;

    @Test
    public void searchBlogTest() throws Exception {
        // given
        String query = "스프링";
        SearchBlogRequest request = new SearchBlogRequest(query, null, null, null);

        //when
        SearchBlogResponse searchBlogResponse = kakaoSearchService.searchBlog(request);

        //then
        assertThat(searchBlogResponse.getDocuments()).isNotEmpty();
        assertThat(searchBlogResponse.getDocuments().get(0).getContents()).contains(query);
    }
}