package com.blog.search.api.service;

import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchResult;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class NaverBlogSearchServiceTest {
    @Autowired
    NaverBlogSearchApi naverBlogSearchApi;

    @Test
    public void searchBlogTest() throws Exception {
        // given
        String query = "스프링";
        BlogSearchRequest request = BlogSearchRequest.builder()
                .query("스프링")
                .page(1)
                .size(10)
                .build();

        //when
        BlogSearchResult blogSearchResult = naverBlogSearchApi.searchBlog(request);

        //then
        assertThat(blogSearchResult.getItems()).isNotEmpty();
        assertThat(blogSearchResult.getItems().get(0).getContents()).contains(query);
    }
}