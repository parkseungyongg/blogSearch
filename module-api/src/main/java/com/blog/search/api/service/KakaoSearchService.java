package com.blog.search.api.service;

import com.blog.search.api.dto.SearchBlogRequest;
import com.blog.search.api.dto.SearchBlogResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Service
@Slf4j
public class KakaoSearchService {
    private final RestTemplate restTemplate;
    private final String kakaoApiUrl;
    private final String restKey;

    @Autowired
    public KakaoSearchService(RestTemplate restTemplate,
                              @Value("${kakao.api.url}") String kakaoApiUrl,
                              @Value("${kakao.api.rest-key}") String restKey) {
        this.restTemplate = restTemplate;
        this.kakaoApiUrl = kakaoApiUrl;
        this.restKey = restKey;
    }

    public SearchBlogResponse searchBlog(SearchBlogRequest request) {
        String uri = UriComponentsBuilder.fromUriString(kakaoApiUrl)
                .path("/v2/search/blog")
                .queryParams(request.toParams())
                .build()
                .toString();

        log.info("uri={}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + restKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        SearchBlogResponse searchBlogResponse;
        try {
            log.info("getBody={}", response.getBody());
            searchBlogResponse = objectMapper.readValue(response.getBody(), new TypeReference<SearchBlogResponse>(){});
        } catch(IOException e) {
            throw new RuntimeException("카카오 API 응답을 읽을 수 없습니다.", e);
        }
        return searchBlogResponse;
    }
}
