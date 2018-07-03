package com.itacademy.jd2.ai.b2b.dao.api.model;

import java.util.Set;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface IOrderObject extends IBaseEntity {
    Boolean getClose();

    IUser getCreator();

    void setClose(Boolean close);

    void setCreator(IUser creator);

    Set<IOrderItem> getOrderItems();

    void setOrderItems(Set<IOrderItem> orderItems);
}
