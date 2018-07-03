package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderItemFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;

public interface IOrderItemDao extends BaseDao<IOrderItem, Integer> {

    IOrderItem getFullInfo(Integer id);

    long getCount(OrderItemFilter filter);

    List<IOrderItem> find(OrderItemFilter filter);

}
