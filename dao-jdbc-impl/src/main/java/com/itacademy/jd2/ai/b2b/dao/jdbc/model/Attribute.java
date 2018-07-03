package com.itacademy.jd2.ai.b2b.dao.jdbc.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;

public class Attribute extends BaseEntity implements IAttribute {

    private String name;
    private String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setValue(final String value) {
        this.value = value;
    }

}
