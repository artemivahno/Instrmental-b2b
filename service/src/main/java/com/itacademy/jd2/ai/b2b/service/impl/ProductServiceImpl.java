package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IProductDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ProductServiceImpl.class);

    @Autowired
    private IProductDao dao;

    @Override
    public IProduct createEntity() {
        final IProduct entity = dao.createEntity();
        entity.setVisible(Boolean.TRUE);
        return entity;
    }

    @Override
    public void save(final IProduct entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved Product: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IProduct get(final Integer id) {
        final IProduct entity = dao.get(id);
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
        LOGGER.info("delete all Product entities");
        dao.deleteAll();
    }

    @Override
    public List<IProduct> getAll() {
        final List<IProduct> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<IProduct> find(final ProductFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final ProductFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IProduct getFullInfo(final Integer id) {
        final IProduct entity = dao.getFullInfo(id);
        LOGGER.debug("entityById: {}", entity);
        return entity;
    }

}
