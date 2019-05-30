package com.hll_sc_app.base.bean;


public class BaseReq<T> {
    private T data;

    public BaseReq() {
    }

    public BaseReq(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
