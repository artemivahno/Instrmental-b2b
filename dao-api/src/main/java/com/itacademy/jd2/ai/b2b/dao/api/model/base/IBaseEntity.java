package com.itacademy.jd2.ai.b2b.dao.api.model.base;

import java.util.Date;

public interface IBaseEntity {
    Integer getId();

    void setId(Integer id);

    Date getCreated();

    void setCreated(Date created);

    Date getUpdated();

    void setUpdated(Date updated);

}
