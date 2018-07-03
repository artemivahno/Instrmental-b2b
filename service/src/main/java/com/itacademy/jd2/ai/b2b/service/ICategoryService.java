package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.CategoryFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;

public interface ICategoryService {

    ICategory get(Integer id);

    List<ICategory> getAll();

    @Transactional
    void save(ICategory entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    ICategory createEntity();

    List<ICategory> find(CategoryFilter filter);

    long getCount(CategoryFilter filter);

}
