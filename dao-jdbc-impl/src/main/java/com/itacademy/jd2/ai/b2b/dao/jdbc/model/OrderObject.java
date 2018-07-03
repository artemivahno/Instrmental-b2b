package com.itacademy.jd2.ai.b2b.dao.jdbc.model;

import java.util.Set;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;

public class OrderObject extends BaseEntity implements IOrderObject {

    private Boolean close;
    private IUser creator;

    @Override
    public Boolean getClose() {
        return close;
    }

    @Override
    public void setClose(final Boolean close) {
        this.close = close;
    }

    @Override
    public IUser getCreator() {
        return creator;
    }

    @Override
    public void setCreator(final IUser creator) {
        this.creator = creator;
    }

    @Override
    public Set<IOrderItem> getOrderItems() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOrderItems(Set<IOrderItem> orderItems) {
        // TODO Auto-generated method stub
        
    }

}
