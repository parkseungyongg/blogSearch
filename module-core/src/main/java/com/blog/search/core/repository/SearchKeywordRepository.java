package com.blog.search.core.repository;

import com.blog.search.core.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findTop10ByOrderByCountDesc();

    SearchKeyword findByKeyword(String keyword);
}
