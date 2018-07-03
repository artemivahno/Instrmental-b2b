package com.itacademy.jd2.ai.b2b.dao.orm.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;

@Entity
public class OrderObject extends BaseEntity implements IOrderObject {

    @Column
    private Boolean close;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUser creator;
    
    @OneToMany(fetch = FetchType.LAZY, targetEntity = OrderItem.class, mappedBy = "orderObject")
    private Set<IOrderItem> orderItems;

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

    public Set<IOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<IOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
