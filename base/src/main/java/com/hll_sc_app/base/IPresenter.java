package com.hll_sc_app.base;

/**
 * <br>
 * <b>功能：</b>MVP中Presenter层协议<br>
 * <b>作者：</b>HuYongcheng<br>
 * <b>日期：</b>2016/10/11<br>
 */
public interface IPresenter<T extends IView> {

    void start();

    void register(T view);

}
