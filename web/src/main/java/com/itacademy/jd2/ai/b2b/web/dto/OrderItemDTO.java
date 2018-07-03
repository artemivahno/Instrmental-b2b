package com.itacademy.jd2.ai.b2b.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class OrderItemDTO {

    private Integer id;

    private Integer quantity;

    @NotNull
    private Integer ordetObjectId;

    private Boolean ordetObjectClose;

    @NotNull
    private Integer productId;

    private String productName;

    private Double productPrice;

    private Date created;

    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderObjectId() {
        return ordetObjectId;
    }

    public void setOrdetObjectId(Integer ordetObjectId) {
        this.ordetObjectId = ordetObjectId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getOrdetObjectId() {
        return ordetObjectId;
    }

    public Boolean getOrdetObjectClose() {
        return ordetObjectClose;
    }

    public void setOrdetObjectClose(Boolean close) {
        this.ordetObjectClose = close;
    }

}
