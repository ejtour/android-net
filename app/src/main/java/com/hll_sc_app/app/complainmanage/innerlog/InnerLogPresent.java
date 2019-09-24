package com.hll_sc_app.app.complainmanage.innerlog;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.ComplainInnerLogResp;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class InnerLogPresent implements IInnerLogContract.IPresent {
    private IInnerLogContract.IView mView;

    public static InnerLogPresent newInstance() {
        return new InnerLogPresent();
    }

    @Override
    public void start() {
        queryInnerLog();
    }

    @Override
    public void register(IInnerLogContract.IView view) {
        mView = view;
    }

    @Override
    public void queryInnerLog() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("complaintID", mView.getComplaintID())
                .put("source", "2")
                .create();
        ComplainManageService.INSTANCE
                .queryComplainInnerLog(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ComplainInnerLogResp>() {
                    @Override
                    public void onSuccess(ComplainInnerLogResp complainInnerLogResp) {
                        mView.queryInnerLogSucess(complainInnerLogResp);
                        queryDropMenus();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }


    @Override
    public void queryDropMenus() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("name", "DepartmentEnum")
                .create();
        ComplainManageService.INSTANCE
                .queryDropMenus(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<DropMenuBean>>() {
                    @Override
                    public void onSuccess(List<DropMenuBean> departmentsBeans) {
                        mView.queryDropMenusSuccess(departmentsBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void saveComplainInnerLog() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("complaintID", mView.getComplaintID())
                .put("issueDepartment", mView.getIssueDepartment())
                .put("relationDepartment", mView.getrelationDepartment())
                .put("result", mView.getResult())
                .put("source", "2")
                .create();
        ComplainManageService.INSTANCE
                .saveComplainInnerLog(baseMapReq)
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
                        mView.saveLogSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
