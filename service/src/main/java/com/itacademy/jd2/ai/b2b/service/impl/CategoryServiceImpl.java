package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.ICategoryDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.CategoryFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDao dao;
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CategoryServiceImpl.class);

    @Override
    public ICategory createEntity() {
        final ICategory entity = dao.createEntity();
        return entity;
    }

    @Override
    public void save(final ICategory entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved Category: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public ICategory get(final Integer id) {
        final ICategory entity = dao.get(id);
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
        LOGGER.info("delete all Category entities");
        dao.deleteAll();
    }

    @Override
    public List<ICategory> getAll() {
        final List<ICategory> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<ICategory> find(final CategoryFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final CategoryFilter filter) {
        return dao.getCount(filter);
    }
}
