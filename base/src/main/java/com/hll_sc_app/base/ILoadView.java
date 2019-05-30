package com.hll_sc_app.base;

public interface ILoadView extends IView {

    void showLoading();

    void hideLoading();

    void showError(UseCaseException e);

    void showToast(String message);

}
