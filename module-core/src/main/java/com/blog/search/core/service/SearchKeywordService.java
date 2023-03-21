package com.blog.search.core.service;

import com.blog.search.core.dto.PopularKeywords;
import com.blog.search.core.dto.SearchKeywordResponse;
import com.blog.search.core.entity.SearchKeyword;
import com.blog.search.core.repository.SearchKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchKeywordService {
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchKeywordService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
    }

    /**
     * 인기 검색어를 조회한다.
     * @return 인기 검색어 리스트
     */
    public PopularKeywords getPopularKeywords() {
        List<SearchKeyword> top10ByOrderByCountDesc = searchKeywordRepository.findTop10ByOrderByCountDesc();

        List<SearchKeywordResponse> searchKeywordResponseList = new ArrayList<>();

        searchKeywordResponseList.addAll(top10ByOrderByCountDesc
                .stream()
                .map(searchKeyword -> SearchKeywordResponse.builder()
                        .keyword(searchKeyword.getKeyword())
                        .count(searchKeyword.getCount())
                        .build()
                ).collect(Collectors.toList())
        );

        PopularKeywords result = new PopularKeywords(searchKeywordResponseList);


        return result;
    }

    /**
     * 검색어를 업데이트 한다.
     * @param keyword 검색어
     */
    public void updateSearchKeyword(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findByKeyword(keyword);

        if (searchKeyword == null) {
            searchKeyword = new SearchKeyword();
            searchKeyword.setKeyword(keyword);
            searchKeyword.setCount(1L);
        } else {
            searchKeyword.setCount(searchKeyword.getCount() + 1);
        }

        searchKeywordRepository.save(searchKeyword);
    }
}
