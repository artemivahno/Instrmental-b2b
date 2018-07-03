package com.itacademy.jd2.ai.b2b.dao.api.filter;

public class OrderItemFilter extends AbstractFilter {

    private Integer orderId;
    
    private Integer quantity;

    private boolean fetchOrderObject;

    private boolean fetchProduct;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public boolean getFetchOrderObject() {
        return fetchOrderObject;
    }

    public void setFetchOrderObject(final boolean fetchOrderObject) {
        this.fetchOrderObject = fetchOrderObject;
    }

    public boolean getFetchProduct() {
        return fetchProduct;
    }

    public void setFetchProduct(final boolean fetchProduct) {
        this.fetchProduct = fetchProduct;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(final Integer orderId) {
        this.orderId = orderId;
    }
    
    

}
