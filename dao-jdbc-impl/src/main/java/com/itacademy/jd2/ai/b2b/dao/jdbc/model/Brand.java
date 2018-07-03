package com.itacademy.jd2.ai.b2b.dao.jdbc.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

public class Brand extends BaseEntity implements IBrand {

    private String name;
    private String description;
    private IImage image;

    @Override
    public String getName() {
        return name;
    }
 
    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public IImage getImage() {
        return image;
    }

    @Override
    public void setImage(final IImage image) {
        this.image = image;
    }
}
