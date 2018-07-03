package com.itacademy.jd2.ai.b2b.dao.api.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.base.IBaseEntity;

public interface IOrderItem extends IBaseEntity {

    Integer getQuantity();

    void setQuantity(Integer quantity);

    IOrderObject getOrderObject();

    void setOrderObject(IOrderObject order);

    IProduct getProduct();

    void setProduct(IProduct product);
}
