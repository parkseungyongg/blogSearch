package com.blog.search.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "search_keyword")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long count;
}
