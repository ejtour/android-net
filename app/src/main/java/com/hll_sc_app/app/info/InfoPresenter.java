package com.hll_sc_app.app.info;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.groupInfo.GroupInfoReq;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

public class InfoPresenter implements IInfoContract.IInfoPresenter {
    private IInfoContract.IInfoView mView;

    @Override
    public void start() {
        SimpleObserver<GroupInfoResp> observer = new SimpleObserver<GroupInfoResp>(mView) {
            @Override
            public void onSuccess(GroupInfoResp groupInfoResp) {
                mView.setData(groupInfoResp);
            }
        };
        GroupInfoReq req = new GroupInfoReq();
        req.setGroupID(UserConfig.getGroupID());
        UserService.INSTANCE.getGroupInfo(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IInfoContract.IInfoView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void reqCertify(CertifyReq req) {
        User.reqCertify(req, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                start();
            }
        });
    }
}
