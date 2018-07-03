package com.itacademy.jd2.ai.b2b.dao.api.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;

public interface IUser extends IBaseEntity {
    String getName();

    String getEmail();

    String getPassword();

    Boolean getEnabled();

    UserRole getRole();

    void setName(String name);

    void setEmail(String email);

    void setPassword(String password);

    void setEnabled(Boolean enabled);

    void setRole(UserRole role);


}
