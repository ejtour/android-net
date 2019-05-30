package com.hll_sc_app.base;

public interface Callback<T> {

    // 请求成功
    void onLoaded(T datas);

    // 异常
    void onError(UseCaseException e);

}
