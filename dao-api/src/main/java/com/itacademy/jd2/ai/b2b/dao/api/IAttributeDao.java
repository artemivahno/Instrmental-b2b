package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.AttributeFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;

public interface IAttributeDao extends BaseDao<IAttribute, Integer> {

    long getCount(AttributeFilter filter);

    List<IAttribute> find(AttributeFilter filter);

}
