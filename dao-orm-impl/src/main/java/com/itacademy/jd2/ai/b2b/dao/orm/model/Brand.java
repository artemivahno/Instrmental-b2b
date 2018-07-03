package com.itacademy.jd2.ai.b2b.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

@Entity
public class Brand extends BaseEntity implements IBrand {

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Image.class)
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
