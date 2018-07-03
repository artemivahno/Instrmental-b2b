package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.ImageFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

public interface IImageService {

    IImage get(Integer id);

    List<IImage> getAll();

    @Transactional
    void save(IImage entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IImage createEntity();

    List<IImage> find(ImageFilter filter);

    long getCount(ImageFilter filter);

}
