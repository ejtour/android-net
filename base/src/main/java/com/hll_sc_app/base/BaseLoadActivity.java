package com.hll_sc_app.base;

import com.hll_sc_app.base.widget.Loading;
import com.hll_sc_app.citymall.util.ToastUtils;

public abstract class BaseLoadActivity extends BaseActivity implements ILoadView {

    @Override
    public void showLoading() {
        Loading.instance().show(this);
    }

    @Override
    public void hideLoading() {
        Loading.instance().dismiss();
    }

    @Override
    public void showError(UseCaseException e) {
        ToastUtils.showShort(this, e.getMsg());
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(this, message);
    }
}
