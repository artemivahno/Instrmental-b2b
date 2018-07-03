package com.itacademy.jd2.ai.b2b.web.dto.search;

public class ProductSearchDTO {

    private String name;

    private Integer categoryId;

    private String categoryName;
    
    private boolean fetchCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isFetchCategory() {
        return fetchCategory;
    }

    public void setFetchCategory(boolean fetchCategory) {
        this.fetchCategory = fetchCategory;
    }



}
