package com.itacademy.jd2.ai.b2b.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;

public interface IUserService {

    IUser get(Integer id);

    List<IUser> getAll();

    IUser enterEmail(String email);

    @Transactional
    void save(IUser entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    @Transactional
    IUser createEntity();

    List<IUser> find(UserFilter filter);

    long getCount(UserFilter filter);

    IUser getByEmail(String email);


}
