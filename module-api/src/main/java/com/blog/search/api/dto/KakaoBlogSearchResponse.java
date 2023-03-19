package com.blog.search.api.dto;

import com.blog.search.api.model.Document;
import com.blog.search.api.model.Meta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoBlogSearchResponse {
    private Meta meta;
    private List<Document> documents;
}
