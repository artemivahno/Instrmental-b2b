package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.AttributeFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;;

public interface IAttributeService {

    IAttribute get(Integer id);

    List<IAttribute> getAll();
    
    @Transactional
    void save(IAttribute entity);
    
    @Transactional
    void delete(Integer id);
    
    @Transactional
    void deleteAll();

    IAttribute createEntity();
    
    List<IAttribute> find(AttributeFilter filter);

    long getCount(AttributeFilter filter);

}
