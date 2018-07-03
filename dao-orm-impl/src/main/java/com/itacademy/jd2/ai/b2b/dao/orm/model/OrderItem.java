package com.itacademy.jd2.ai.b2b.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

@Entity
public class OrderItem extends BaseEntity implements IOrderItem {

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OrderObject.class)
    private IOrderObject orderObject;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Product.class)
    private IProduct product;

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public IOrderObject getOrderObject() {
        return orderObject;
    }

    @Override
    public void setOrderObject(final IOrderObject orderObject) {
        this.orderObject = orderObject;
    }

    @Override
    public IProduct getProduct() {
        return product;
    }

    @Override
    public void setProduct(final IProduct product) {
        this.product = product;
    }


}
