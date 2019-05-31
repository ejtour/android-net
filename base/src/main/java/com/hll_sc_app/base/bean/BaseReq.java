package com.hll_sc_app.base.bean;


/**
 * 构造请求参数
 *
 * @author zhuyingsong
 * @date 2019/4/23
 */
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
