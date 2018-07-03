package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IOrderItemDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderItemFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.service.IOrderItemService;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(OrderItemServiceImpl.class);

    @Autowired
    private IOrderItemDao dao;

    @Override
    public IOrderItem createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IOrderItem entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved OrderItem: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IOrderItem get(final Integer id) {
        final IOrderItem entity = dao.get(id);
        LOGGER.info("entityById: {}" + entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete entity: {}" + id);
        dao.delete(id);

    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all OrderItem entities");
        dao.deleteAll();
    }

    @Override
    public List<IOrderItem> getAll() {
        final List<IOrderItem> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<IOrderItem> find(OrderItemFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(OrderItemFilter filter) {
        // TODO Auto-generated method stub
        return dao.getCount(filter);
    }

}
