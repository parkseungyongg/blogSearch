package com.blog.search.api.service;

import com.blog.search.api.dto.BlogSearchRequest;
import com.blog.search.api.dto.BlogSearchResponse;
import com.blog.search.api.dto.BlogSearchResult;
import com.blog.search.api.dto.KakaoBlogSearchResponse;
import com.blog.search.api.exception.KakaoApiException;
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

@Service
@Slf4j
public class KakaoBlogSearchApi implements BlogSearchApi {
    private final RestTemplate restTemplate;
    private final String kakaoApiUrl;
    private final String restKey;

    @Autowired
    public KakaoBlogSearchApi(RestTemplate restTemplate,
                                  @Value("${kakao.api.url}") String kakaoApiUrl,
                                  @Value("${kakao.api.rest-key}") String restKey) {
        this.restTemplate = restTemplate;
        this.kakaoApiUrl = kakaoApiUrl;
        this.restKey = restKey;
    }

    public BlogSearchResult searchBlog(BlogSearchRequest request) {
        String uri = UriComponentsBuilder.fromUriString(kakaoApiUrl)
                .path("/v2/search/blog")
                .queryParams(request.toParams(ProviderType.KAKAO))
                .build()
                .toString();

        log.debug("Kakao API URI={}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + restKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoBlogSearchResponse kakaoBlogSearchResponse;
        try {
            log.debug("Kakao API response body={}", response.getBody());
            kakaoBlogSearchResponse = objectMapper.readValue(response.getBody(), new TypeReference<KakaoBlogSearchResponse>(){});
        } catch(IOException e) {
            throw new KakaoApiException("카카오 API 응답을 읽을 수 없습니다.", e);
        }

        List<BlogSearchResponse> items = kakaoBlogSearchResponse.getDocuments().stream()
                .map(document -> BlogSearchResponse
                        .builder()
                        .title(document.getTitle())
                        .blogname(document.getBlogname())
                        .contents(document.getContents())
                        .datetime(document.getDatetime())
                        .thumbnail(document.getThumbnail())
                        .url(document.getUrl())
                        .build()
                )
                .collect(Collectors.toList());

        return BlogSearchResult.builder()
                .items(items)
                .page(request.getPage())
                .size(request.getSize())
                .totalElements(kakaoBlogSearchResponse.getMeta().getTotalCount())
                .totalPages((int) Math.ceil((double) kakaoBlogSearchResponse.getMeta().getTotalCount() / request.getSize()))
                .sortType(request.getSort())
                .build();
    }
}
