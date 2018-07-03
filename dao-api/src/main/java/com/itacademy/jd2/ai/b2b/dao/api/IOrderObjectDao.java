package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;

public interface IOrderObjectDao extends BaseDao<IOrderObject, Integer> {
    
    long getCount(OrderObjectFilter filter);

    IOrderObject getFullInfo(Integer id);

    List<IOrderObject> find(OrderObjectFilter filter);

}
