package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;

public interface IOrderObjectService {

    IOrderObject get(Integer id);

    @Transactional
    void save(IOrderObject entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IOrderObject createEntity();

    List<IOrderObject> getAll();

    List<IOrderObject> find(OrderObjectFilter filter);

    long getCount(OrderObjectFilter filter);

    IOrderObject getFullinfo(Integer id);




}
