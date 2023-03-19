package com.blog.search.api.service;

import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.KakaoBlogSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
class KakaoBlogSearchServiceTest {
    @Autowired
    KakaoBlogSearchService kakaoBlogSearchService;

    @Test
    public void searchBlogTest() throws Exception {
        // given
        String query = "스프링";
        BlogSearchRequest request = new BlogSearchRequest(query, null, null, null);

        //when
        KakaoBlogSearchResponse kakaoBlogSearchResponse = kakaoBlogSearchService.searchBlog(request);

        //then
        assertThat(kakaoBlogSearchResponse.getDocuments()).isNotEmpty();
        assertThat(kakaoBlogSearchResponse.getDocuments().get(0).getContents()).contains(query);
    }
}