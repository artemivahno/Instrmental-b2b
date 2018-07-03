package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.CategoryFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;

public interface ICategoryDao extends BaseDao<ICategory, Integer> {

    List<ICategory> find(CategoryFilter filter);

    long getCount(CategoryFilter filter);

    ICategory getFullInfo(Integer id);

}
