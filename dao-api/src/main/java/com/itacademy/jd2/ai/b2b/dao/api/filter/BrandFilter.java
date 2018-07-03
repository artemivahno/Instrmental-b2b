package com.itacademy.jd2.ai.b2b.dao.api.filter;

public class BrandFilter extends AbstractFilter {

    private String name;

    public boolean fetchImage;

    public boolean getFetchImage() {
        return fetchImage;
    }

    public void setFetchImage(final boolean fetchImage) {
        this.fetchImage = fetchImage;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
