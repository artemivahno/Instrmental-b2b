package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.BrandFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;

public interface IBrandDao extends BaseDao<IBrand, Integer> {

    long getCount(BrandFilter filter);

    List<IBrand> find(BrandFilter filter);

}
