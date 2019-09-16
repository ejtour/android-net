package com.hll_sc_app.app.staffmanage.detail.depart;

import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.staff.DepartmentListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class DepartListPresent implements IDepartListContract.IPresent {
    private IDepartListContract.IView mView;
    private int pageSize = 20;
    private int pageNum = 1;
    private int pageNumTemp = 1;

    public static DepartListPresent newInstance() {
        return new DepartListPresent();
    }

    @Override
    public void register(IDepartListContract.IView view) {
        mView = view;
    }

    @Override
    public void queryDepartments(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("pageNum", pageNumTemp + "")
                .put("pageSize", pageSize + "")
                .put("groupID", userBean.getGroupID())
                .put("deptName", mView.getDepartName())
                .create();
        StaffManageService.INSTANCE
                .queryDepartments(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<DepartmentListResp>() {
                    @Override
                    public void onSuccess(DepartmentListResp resp) {
                        mView.querySuccess(resp, pageNumTemp > 1);
                        pageNum = pageNumTemp;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageNumTemp = pageNum;
                        mView.showError(e);
                    }
                });

    }

    @Override
    public void addDepartment(String name) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("deptName", name)
                .put("groupID", userBean.getGroupID())
                .create();
        StaffManageService.INSTANCE
                .addDepartment(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.addSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void getMore() {
        pageNumTemp++;
        queryDepartments(false);
    }

    @Override
    public void refresh() {
        pageNumTemp = 1;
        queryDepartments(false);
    }

    @Override
    public int getPageSize() {
        return 0;
    }
}
