package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IImageDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ImageFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.service.IImageService;

@Service
public class ImageServiceImpl implements IImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    private IImageDao dao;

    @Override
    public IImage createEntity() {
        final IImage entity = dao.createEntity();
        return entity;
    }

    @Override
    public void save(final IImage entity) {
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved Image: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IImage get(final Integer id) {
        final IImage entity = dao.get(id);
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
        LOGGER.info("delete all Image entities");
        dao.deleteAll();
    }

    @Override
    public List<IImage> getAll() {
        final List<IImage> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }
    
    @Override
    public List<IImage> find(final ImageFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final ImageFilter filter) {
        return dao.getCount(filter);
    }

}
