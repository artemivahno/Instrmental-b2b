package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IAttributeDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.AttributeFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.service.IAttributeService;

@Service
public class AttributeServiceImpl implements IAttributeService {

    @Autowired
    private IAttributeDao dao;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AttributeServiceImpl.class);

    @Override
    public IAttribute createEntity() {
        final IAttribute entity = dao.createEntity();
        return entity;
    }

    @Override
    public void save(final IAttribute entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved Attribute: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IAttribute get(final Integer id) {
        final IAttribute entity = dao.get(id);
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
        LOGGER.info("delete all Attribute entities");
        dao.deleteAll();
    }

    @Override
    public List<IAttribute> getAll() {
        final List<IAttribute> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<IAttribute> find(final AttributeFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final AttributeFilter filter) {
        return dao.getCount(filter);
    }

}
