package com.hll_sc_app.app.complainmanage.innerlog;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.ComplainInnerLogResp;
import com.hll_sc_app.bean.complain.DepartmentsBean;
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
        queryDepartments();
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
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }


    @Override
    public void queryDepartments() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("name", "DepartmentEnum")
                .create();
        ComplainManageService.INSTANCE
                .queryDepartments(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<DepartmentsBean>>() {
                    @Override
                    public void onSuccess(List<DepartmentsBean> departmentsBeans) {
                        mView.queryDepartmentsSuccess(departmentsBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
