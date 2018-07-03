package com.itacademy.jd2.ai.b2b.dao.api.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface IBrand extends IBaseEntity {

    String getName();

    String getDescription();

    IImage getImage();

    void setName(String name);

    void setDescription(String description);

    void setImage(IImage image);
}
