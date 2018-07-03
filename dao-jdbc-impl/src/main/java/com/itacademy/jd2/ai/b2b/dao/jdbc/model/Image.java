package com.itacademy.jd2.ai.b2b.dao.jdbc.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

public class Image extends BaseEntity implements IImage {

    private String name;
    private String url;
    private Integer position;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Integer position) {
        this.position = position;
    }

}
