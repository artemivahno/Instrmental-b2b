package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderItemFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;

public interface IOrderItemService {

    IOrderItem get(Integer id);

    @Transactional
    void save(IOrderItem entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IOrderItem createEntity();

    List<IOrderItem> getAll();

    List<IOrderItem> find(OrderItemFilter filter);

    long getCount(OrderItemFilter filter);

}
