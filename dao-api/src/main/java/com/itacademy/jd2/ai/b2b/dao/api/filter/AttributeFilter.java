package com.itacademy.jd2.ai.b2b.dao.api.filter;

public class AttributeFilter extends AbstractFilter {

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}
