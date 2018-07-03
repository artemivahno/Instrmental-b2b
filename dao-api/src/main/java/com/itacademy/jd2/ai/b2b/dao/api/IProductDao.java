package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

public interface IProductDao extends BaseDao<IProduct, Integer> {

    List<IProduct> find(ProductFilter filter);

    long getCount(ProductFilter filter);

    IProduct getFullInfo(Integer id);

}
