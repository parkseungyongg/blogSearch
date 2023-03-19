package com.blog.search.api.model;

public enum BlogSortType {
    ACCURACY("accuracy"), RECENCY("recency");

    private final String value;

    BlogSortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
