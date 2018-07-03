package com.itacademy.jd2.ai.b2b.dao.api.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface IImage extends IBaseEntity {

    String getName();

    String getUrl();

    Integer getPosition();

    void setName(String name);

    void setUrl(String url);

    void setPosition(Integer position);

}
