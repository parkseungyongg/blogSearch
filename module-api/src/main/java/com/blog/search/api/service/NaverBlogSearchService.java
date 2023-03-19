package com.blog.search.api.service;

import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.NaverBlogSearchResponse;
import com.blog.search.api.exception.NaverApiException;
import com.blog.search.api.model.ProviderType;
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

@Slf4j
@Service
public class NaverBlogSearchService {
    private final RestTemplate restTemplate;
    private final String naverApiUrl;
    private final String naverClientId;
    private final String naverClientSecret;

    @Autowired
    public NaverBlogSearchService(RestTemplate restTemplate,
                                  @Value("${naver.api.url}") String naverApiUrl,
                                  @Value("${naver.api.client-id}") String naverClientId,
                                  @Value("${naver.api.client-secret}") String naverClientSecret) {
        this.restTemplate = restTemplate;
        this.naverApiUrl = naverApiUrl;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
    }

    public NaverBlogSearchResponse searchBlog(BlogSearchRequest request) {
        String uri = UriComponentsBuilder.fromUriString(naverApiUrl)
                .path("/v1/search/blog")
                .queryParams(request.toParams(ProviderType.NAVER))
                .build()
                .toString();

        log.debug("Naver API URI={}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", naverClientId);
        headers.add("X-Naver-Client-Secret", naverClientSecret);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        NaverBlogSearchResponse naverBlogSearchResponse;
        try {
            log.debug("Naver API response body={}", response.getBody());
            naverBlogSearchResponse = objectMapper.readValue(response.getBody(), new TypeReference<NaverBlogSearchResponse>() {
            });
        } catch (IOException e) {
            throw new NaverApiException("Naver API 응답을 읽을 수 없습니다.", e);
        }
        return naverBlogSearchResponse;
    }
}
