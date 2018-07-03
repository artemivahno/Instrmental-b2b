package com.itacademy.jd2.ai.b2b.dao.jdbc.model;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

public class OrderItem extends BaseEntity implements IOrderItem {

    private Integer quantity;
    private IOrderObject orderObject;
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
