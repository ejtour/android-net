package com.hll_sc_app.app.setting.group;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/13
 */

class GroupSettingPresenter implements IGroupSettingContract.IGroupSettingPresenter {
    private IGroupSettingContract.IGroupSettingView mView;

    public static GroupSettingPresenter newInstance() {
        return new GroupSettingPresenter();
    }

    private GroupSettingPresenter() {
    }

    @Override
    public void toggle(int type, boolean isChecked) {
        User.changeGroupParam(type, isChecked ? 2 : 1, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success(isChecked);
            }

            @Override
            public void onFailure(UseCaseException e) {
                super.onFailure(e);
                mView.fallbackStatus();
            }
        });
    }

    @Override
    public void start() {
        User.queryGroupParam(mView.getTypes(), new SimpleObserver<List<GroupParamBean>>(mView) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                mView.setData(groupParamBeans);
            }
        });
    }

    @Override
    public void register(IGroupSettingContract.IGroupSettingView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
