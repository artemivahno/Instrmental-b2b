package com.itacademy.jd2.ai.b2b.dao.api.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface IAttribute extends IBaseEntity {
    String getName();

    String getValue();

    void setName(String name);

    void setValue(String value);

}
