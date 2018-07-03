package com.itacademy.jd2.ai.b2b.dao.api.filter;

public class CategoryFilter extends AbstractFilter {

    private String name;

    private Integer position;
    
    private boolean fetchImage;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final Integer position) {
        this.position = position;
    }

    public boolean getFetchImage() {
        return fetchImage;
    }

    public void setFetchImage(final boolean fetchImage) {
        this.fetchImage = fetchImage;
    }


}
