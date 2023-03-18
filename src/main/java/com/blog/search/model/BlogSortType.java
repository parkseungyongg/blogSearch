package com.blog.search.model;

public enum BlogSortType {
    ACCURACY("accuracy"), LATEST("recency");

    private final String value;

    BlogSortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
