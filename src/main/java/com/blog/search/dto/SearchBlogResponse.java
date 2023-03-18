package com.blog.search.dto;

import com.blog.search.model.Documents;
import com.blog.search.model.Meta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchBlogResponse {
    private Meta meta;
    private List<Documents> documents;
}
