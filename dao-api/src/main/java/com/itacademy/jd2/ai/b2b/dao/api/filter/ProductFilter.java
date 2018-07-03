package com.itacademy.jd2.ai.b2b.dao.api.filter;

import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;

public class ProductFilter extends AbstractFilter {

    private String name;

    private Boolean visible;

    private Double price;

    private boolean fetchBrand;
    
    private boolean fetchCategory;
    
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean getFetchBrand() {
        return fetchBrand;
    }

    public void setFetchBrand(boolean fetchBrand) {
        this.fetchBrand = fetchBrand;
    }

    public boolean isFetchCategory() {
        return fetchCategory;
    }

    public void setFetchCategory(boolean fetchCategory) {
        this.fetchCategory = fetchCategory;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
