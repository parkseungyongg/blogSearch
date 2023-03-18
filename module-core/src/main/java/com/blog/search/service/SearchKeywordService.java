package com.blog.search.service;

import com.blog.search.dto.SearchKeywordResponse;
import com.blog.search.entity.SearchKeyword;
import com.blog.search.repository.SearchKeywordRepository;
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

    public List<SearchKeywordResponse> getPopularKeywords() {
        List<SearchKeyword> top10ByOrderByCountDesc = searchKeywordRepository.findTop10ByOrderByCountDesc();

        List<SearchKeywordResponse> result = new ArrayList<>();

        result.addAll(top10ByOrderByCountDesc
                .stream()
                .map(searchKeyword -> SearchKeywordResponse.builder()
                        .keyword(searchKeyword.getKeyword())
                        .count(searchKeyword.getCount())
                        .build()
                ).collect(Collectors.toList())
        );

        return result;
    }

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
