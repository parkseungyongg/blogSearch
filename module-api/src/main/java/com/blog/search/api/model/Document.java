package com.blog.search.api.model;

import lombok.Getter;

@Getter
public class Document {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String datetime;
    private String thumbnail;
}
