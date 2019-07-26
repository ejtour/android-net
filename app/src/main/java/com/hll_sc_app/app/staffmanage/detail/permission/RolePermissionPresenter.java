package com.hll_sc_app.app.staffmanage.detail.permission;

import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.staff.RolePermissionResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 岗位权限简介
 *
 * @author zhuyingsong
 * @date 2018/7/26
 */
public class RolePermissionPresenter implements RolePermissionContract.IRolePermissionPresenter {
    private RolePermissionContract.IRolePermissionView mView;

    static RolePermissionPresenter newInstance() {
        return new RolePermissionPresenter();
    }

    @Override
    public void start() {
        queryRolesPermission();
    }

    @Override
    public void register(RolePermissionContract.IRolePermissionView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryRolesPermission() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("authorityType", "2")
            .create();
        StaffManageService.INSTANCE
            .queryRolesPermission(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<RolePermissionResp>>() {
                @Override
                public void onSuccess(List<RolePermissionResp> result) {
                    mView.showList(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }
}
