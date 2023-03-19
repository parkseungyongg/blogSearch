package com.blog.search.api.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Item {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;
}
