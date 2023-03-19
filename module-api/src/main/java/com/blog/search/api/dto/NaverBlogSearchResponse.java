package com.blog.search.api.dto;

import com.blog.search.api.model.Item;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverBlogSearchResponse {
    private List<Item> items;

    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
}
