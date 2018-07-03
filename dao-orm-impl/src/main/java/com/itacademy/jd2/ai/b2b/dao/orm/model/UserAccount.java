package com.itacademy.jd2.ai.b2b.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;

@Entity
public class UserAccount extends BaseEntity implements IUser {


    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Boolean enabled;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    @Override
    public void setRole(final UserRole role) {
        this.role = role;
    }

}
