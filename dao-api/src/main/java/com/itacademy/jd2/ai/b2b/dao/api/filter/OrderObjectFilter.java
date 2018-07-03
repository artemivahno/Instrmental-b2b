package com.itacademy.jd2.ai.b2b.dao.api.filter;

public class OrderObjectFilter extends AbstractFilter {

    private Boolean close;

    private boolean fetchUser;
    
    private Integer userId;
    
    private boolean fetchOrderItem;



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public boolean getFetchUser() {
        return fetchUser;
    }

    public void setFetchUser(final boolean fetchUser) {
        this.fetchUser = fetchUser;
    }

    public Boolean getClose() {
        return close;
    }

    public void setClose(final Boolean close) {
        this.close = close;
    }

    public boolean isFetchOrderItem() {
        return fetchOrderItem;
    }

    public void setFetchOrderItem(boolean fetchOrderItem) {
        this.fetchOrderItem = fetchOrderItem;
    }

}
