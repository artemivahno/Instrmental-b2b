package com.itacademy.jd2.ai.b2b.dao.api.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface ICategory extends IBaseEntity {
    String getName();

    String getDescription();

    Integer getPosition();

    IImage getImage();

    void setName(String name);

    void setDescription(String description);

    void setPosition(Integer position);

    void setImage(IImage image);
}
