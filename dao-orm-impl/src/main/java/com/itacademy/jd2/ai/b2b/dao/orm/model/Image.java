package com.itacademy.jd2.ai.b2b.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

@Entity
public class Image extends BaseEntity implements IImage {

    @Column
    private String name;
    
    @Column
    private String url;
    
    @Column
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
