package com.itacademy.jd2.ai.b2b.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.ai.b2b.dao.api.IUserDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;
import com.itacademy.jd2.ai.b2b.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao dao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;

    @Override
    public IUser createEntity() {
        final IUser entity = dao.createEntity();
        entity.setEnabled(Boolean.TRUE);
        entity.setRole(UserRole.customer);
        return entity;
    }
    
    

    @Override
    public void save(final IUser entity) {
        /*IUser user = userService.getByEmail();*/
        final Date modifedOn = new Date();
        entity.setUpdated(modifedOn);
        if (entity.getId() == null) {
            /*if (entity.getEmail().equals(enterEmail(user.getEmail()))) {
            
            /*entity.setRole(UserRole.customer);*/
            /*entity.setPassword(passwordEncoder.encode(entity.getPassword()));*/
            entity.setCreated(modifedOn);
            dao.insert(entity);
            LOGGER.info("new saved User: {}" + entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public IUser get(final Integer id) {
        final IUser entity = dao.get(id);
        LOGGER.info("entityById: {}" + entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete entity: {}" + id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all User entities");
        dao.deleteAll();
    }

    @Override
    public List<IUser> getAll() {
        final List<IUser> all = dao.selectAll();
        LOGGER.info("total count in DB: {}" + all.size());
        return all;
    }

    @Override
    public List<IUser> find(final UserFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final UserFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IUser enterEmail(String email) { // FOR LOGIN
        return dao.enterEmail(email);
    }

    @Override
    public IUser getByEmail(final String login) {
        return dao.getByEmail(login);

    }
}
