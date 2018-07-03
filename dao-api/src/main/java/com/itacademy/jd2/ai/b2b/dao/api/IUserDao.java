package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;

public interface IUserDao extends BaseDao<IUser, Integer> {
    
    long getCount(UserFilter filter);

    List<IUser> find(UserFilter filter);

    IUser enterEmail(String email);

    IUser getByEmail(String login);

}
