package com.hll_sc_app.app.setting;


import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.text.TextUtils;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class ILogoffPresenter implements LogoffContract.ILogoffPresenter {

    private LogoffContract.ILogoffView mView;

    public static ILogoffPresenter newInstance() {
        return new ILogoffPresenter();
    }

    private ILogoffPresenter() {

    }

    @Override
    public void logout(SimpleObserver<Object> observer) {
        UserBean userBean = GreenDaoUtils.getUser();
        UserService.INSTANCE
                .logoffAccount(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID(), true)
                        .put("oemID", userBean.getOemID(), true)
                        .put("unionId", userBean.getUnionId(), true)
                        .put("odmId", userBean.getOdmId(), true)
                        .put("resourceType", userBean.getResourceType(), true)
                        .put("accountType", userBean.getAccountType(), true)
                        .put("loginStatus", userBean.getLoginStatus(), true)
                        .put("loginPhone", userBean.getLoginPhone(), true)
                        .put("roleID", userBean.getRoleID(), true)
                        .put("employeeType", userBean.getEmployeeType(), true)
                        .put("employeeCode", userBean.getEmployeeCode(), true)
                        .put("employeeID", userBean.getEmployeeID(), true)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);

    }

    @Override
    public void register(LogoffContract.ILogoffView view) {
        this.mView = view;
    }
}
