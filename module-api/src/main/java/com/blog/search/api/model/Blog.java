package com.blog.search.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Blog {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String datetime;
    private String thumbnail;
}
