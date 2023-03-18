package com.blog.search.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogSearchResponse {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String datetime;
    private String thumbnail;
}
