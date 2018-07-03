package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

public interface BaseDao<ENTITY, ID> {

    ENTITY createEntity();

    ENTITY get(ID id);

    void update(ENTITY entity);

    void insert(ENTITY entity);

    void delete(ID id);

    void deleteAll();

    List<ENTITY> selectAll();
}
