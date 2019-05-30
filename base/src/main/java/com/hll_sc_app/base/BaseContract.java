package com.hll_sc_app.base;

/**
 * @auther 胡永城
 * @date 2017/4/5
 */
public interface BaseContract {

    interface IView<T extends IPresenter> {

        void setPresenter(T presenter);

        boolean isActive();

    }

    interface IPresenter {

        void start();

    }

}
