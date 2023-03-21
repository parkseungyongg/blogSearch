package com.blog.search.core.service;

import com.blog.search.SearchBlogCoreApplication;
import com.blog.search.core.dto.PopularKeywords;
import com.blog.search.core.entity.SearchKeyword;
import com.blog.search.core.repository.SearchKeywordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchBlogCoreApplication.class)
class SearchKeywordServiceTest {

    @Autowired
    SearchKeywordService searchKeywordService;
    @Autowired
    SearchKeywordRepository searchKeywordRepository;

    @Test
    @DisplayName("검색 키워드가 한개인 경우 1개 반환한다.")
    void getPopularKeywords_keywordOneCount() {
        // given
        String keyword = "스프링";
        searchKeywordService.updateSearchKeyword(keyword);

        // when
        PopularKeywords popularKeywords = searchKeywordService.getPopularKeywords();

        // then
        assertThat(popularKeywords.getPopularKeywords()).isNotEmpty();
        assertThat(popularKeywords.getPopularKeywords().size()).isEqualTo(1);
        assertThat(popularKeywords.getPopularKeywords().get(0).getKeyword()).isEqualTo(keyword);
        assertThat(popularKeywords.getPopularKeywords().get(0).getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("검색 키워드가 없는 경우 빈리스트를 반환한다.")
    void getPopularKeywords_keywordEmpty() {
        // given

        // when
        PopularKeywords popularKeywords = searchKeywordService.getPopularKeywords();

        // then
        assertThat(popularKeywords.getPopularKeywords()).isEmpty();
        assertThat(popularKeywords.getPopularKeywords().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("검색 키워드가 10개 이상인 경우 10개가 반환되어야 한다.")
    void getPopularKeywords_keywordOverTen() {
        // given
        String keyword = "스프링";
        for(int i=1; i<=11; i++) {
            searchKeywordService.updateSearchKeyword(keyword + i);
        }

        // when
        PopularKeywords popularKeywords = searchKeywordService.getPopularKeywords();

        // then
        assertThat(popularKeywords.getPopularKeywords()).isNotEmpty();
        assertThat(popularKeywords.getPopularKeywords().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("검색 키워드가 중복되는 경우")
    void getPopularKeywords_keywordDuplicated() {
        // given
        String keywordA = "스프링A";
        String keywordB = "스프링B";

        searchKeywordService.updateSearchKeyword(keywordA);
        searchKeywordService.updateSearchKeyword(keywordA);
        searchKeywordService.updateSearchKeyword(keywordA);

        searchKeywordService.updateSearchKeyword(keywordB);
        searchKeywordService.updateSearchKeyword(keywordB);

        // when
        PopularKeywords popularKeywords = searchKeywordService.getPopularKeywords();

        // then
        assertThat(popularKeywords.getPopularKeywords()).isNotEmpty();
        assertThat(popularKeywords.getPopularKeywords().size()).isEqualTo(2);
        assertThat(popularKeywords.getPopularKeywords().get(0).getKeyword()).isEqualTo("스프링A");
        assertThat(popularKeywords.getPopularKeywords().get(0).getCount()).isEqualTo(3);
        assertThat(popularKeywords.getPopularKeywords().get(1).getKeyword()).isEqualTo("스프링B");
        assertThat(popularKeywords.getPopularKeywords().get(1).getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("검색어가 존재하는 경우 기존 count에 1을 더한다.")
    void updateSearchKeyword_ExistingKeyword() {
        // given
        String keyword = "스프링";
        searchKeywordService.updateSearchKeyword(keyword);

        // when
        searchKeywordService.updateSearchKeyword(keyword);

        // then
        SearchKeyword searchKeyword = searchKeywordRepository.findByKeyword(keyword);
        assertThat(searchKeyword.getCount()).isEqualTo(2L);
    }

    @Test
    @DisplayName("검색어가 존재하지 않는 경우, 새로운 검색어가 저장된다.")
    void updateSearchKeyword_NewKeyword() {
        // given
        String keyword = "스프링";

        // when
        searchKeywordService.updateSearchKeyword(keyword);

        // then
        SearchKeyword createdSearchKeyword = searchKeywordRepository.findByKeyword(keyword);
        assertThat(createdSearchKeyword.getCount()).isEqualTo(1L);
    }

    @AfterEach
    void afterEach() {
        searchKeywordRepository.deleteAll();
    }
}