package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.BrandFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;

public interface IBrandService {

    IBrand get(Integer id);

    List<IBrand> getAll();

    @Transactional
    void save(IBrand entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IBrand createEntity();

    List<IBrand> find(BrandFilter filter);

    long getCount(BrandFilter filter);

}
