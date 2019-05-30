package com.hll_sc_app.base;

import com.hll_sc_app.base.widget.Loading;
import com.hll_sc_app.citymall.util.ToastUtils;

/**
 * 显示Loading的Fragment基类
 *
 * @author 胡永城
 * @date 2017/9/15
 */
public class BaseLoadFragment extends BaseFragment implements ILoadView {

    @Override
    public void showLoading() {
        Loading.instance().show(getActivity());
    }

    @Override
    public void hideLoading() {
        Loading.instance().dismiss();
    }

    @Override
    public void showError(UseCaseException e) {
        ToastUtils.showShort(getActivity(), e.getMsg());
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(getActivity(), message);
    }
}
