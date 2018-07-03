package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IBrandDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.BrandFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.service.IBrandService;

@Service
public class BrandServiceImpl implements IBrandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private IBrandDao dao;

    @Override
    public IBrand createEntity() {
        final IBrand entity = dao.createEntity();
        return entity;
    }

    @Override
    public void save(final IBrand entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved Brand: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IBrand get(final Integer id) {
        final IBrand entity = dao.get(id);
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
        LOGGER.info("delete all Brand entities");
        dao.deleteAll();
    }

    @Override
    public List<IBrand> getAll() {
        final List<IBrand> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<IBrand> find(final BrandFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final BrandFilter filter) {
        return dao.getCount(filter);
    }

}
