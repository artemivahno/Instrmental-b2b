package com.itacademy.jd2.ai.b2b.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;

@Entity
public class Attribute extends BaseEntity implements IAttribute {

    @Column
    private String name;

    @Column
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
