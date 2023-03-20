package com.blog.search.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 검색 키워드를 저장하는 엔티티 클래스
 */
@Entity
@Table(name = "search_keyword")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchKeyword {

    /** 검색 키워드 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 검색 키워드 */
    @Column(nullable = false)
    private String keyword;

    /** 검색 횟수 */
    @Column(nullable = false)
    private Long count;
}
