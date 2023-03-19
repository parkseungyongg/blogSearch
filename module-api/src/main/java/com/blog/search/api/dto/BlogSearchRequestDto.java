package com.blog.search.api.dto;

import com.blog.search.api.model.BlogSortType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Slf4j
public class BlogSearchRequestDto {

    @NotBlank
    private String query;

    @Min(1)
    @Max(50)
    private int page = 1;

    @Min(1)
    @Max(50)
    private int size = 10;

    private BlogSortType sort = BlogSortType.ACCURACY;

    public BlogSortType getSort() {
        if(sort == null) {
            return BlogSortType.ACCURACY;
        } else {
            return sort;
        }
    }
}
