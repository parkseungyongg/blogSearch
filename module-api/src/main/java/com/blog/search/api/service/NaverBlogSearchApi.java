package com.blog.search.api.service;

import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchResponse;
import com.blog.search.api.dto.BlogSearchResult;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NaverBlogSearchApi implements BlogSearchApi {
    private final RestTemplate restTemplate;
    private final String naverApiUrl;
    private final String naverClientId;
    private final String naverClientSecret;

    @Autowired
    public NaverBlogSearchApi(RestTemplate restTemplate,
                              @Value("${naver.api.url}") String naverApiUrl,
                              @Value("${naver.api.client-id}") String naverClientId,
                              @Value("${naver.api.client-secret}") String naverClientSecret) {
        this.restTemplate = restTemplate;
        this.naverApiUrl = naverApiUrl;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
    }

    public BlogSearchResult searchBlog(BlogSearchRequest request) {
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

        List<BlogSearchResponse> items = naverBlogSearchResponse.getItems().stream()
                .map(item -> BlogSearchResponse.builder()
                        .title(item.getTitle())
                        .url(item.getBloggerlink())
                        .contents(item.getDescription())
                        .blogname(item.getBloggername())
                        .build())
                .collect(Collectors.toList());

        return BlogSearchResult.builder()
                .items(items)
                .size(request.getSize())
                .page(request.getPage())
                .sortType(request.getSort())
                .totalPages(naverBlogSearchResponse.getTotal() / request.getSize())
                .totalElements(naverBlogSearchResponse.getTotal())
                .build();
    }
}
