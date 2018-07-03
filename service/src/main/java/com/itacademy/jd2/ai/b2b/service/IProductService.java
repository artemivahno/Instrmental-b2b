package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

public interface IProductService {

    IProduct get(Integer id);

    List<IProduct> getAll();

    @Transactional
    void save(IProduct entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IProduct createEntity();

    List<IProduct> find(ProductFilter filter);

    long getCount(ProductFilter filter);

    IProduct getFullInfo(Integer id);

}
