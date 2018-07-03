package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IOrderObjectDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.service.IOrderObjectService;

@Service
public class OrderServiceImpl implements IOrderObjectService {

    @Autowired
    private IOrderObjectDao dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public IOrderObject createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IOrderObject entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved Order: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IOrderObject get(final Integer id) {
        final IOrderObject entity = dao.get(id);
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
        LOGGER.info("delete all Order entities");
        dao.deleteAll();
    }

    @Override
    public List<IOrderObject> getAll() {
        final List<IOrderObject> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<IOrderObject> find(final OrderObjectFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final OrderObjectFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IOrderObject getFullinfo(Integer id) {
        final IOrderObject entity = dao.getFullInfo(id);
        LOGGER.debug("entityById: {}", entity);
        return entity;
    }

}
