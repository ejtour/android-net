package com.hll_sc_app.app.setting.remind;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.user.RemindResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public class RemindSettingPresenter implements IRemindSettingContract.IRemindSettingPresenter {
    private IRemindSettingContract.IRemindSettingView mView;

    private RemindSettingPresenter() {
    }

    public static RemindSettingPresenter newInstance() {
        return new RemindSettingPresenter();
    }

    @Override
    public void update(boolean open, String times) {
        User.updateRemind(open, times, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("设置成功");
                mView.success();
            }
        });
    }

    @Override
    public void start() {
        User.queryRemind(new SimpleObserver<RemindResp>(mView) {
            @Override
            public void onSuccess(RemindResp remindResp) {
                mView.setData(remindResp);
            }
        });
    }

    @Override
    public void register(IRemindSettingContract.IRemindSettingView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
