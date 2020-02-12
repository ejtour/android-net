package com.hll_sc_app.app.contractmanage.detail;

import com.hll_sc_app.api.ContractService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ContractManageDetailPresent implements IContractManageDetailContract.IPresent {
    private IContractManageDetailContract.IView mView;

    public static ContractManageDetailPresent newInstance() {
        return new ContractManageDetailPresent();
    }

    @Override
    public void register(IContractManageDetailContract.IView view) {
        mView = view;
    }


    @Override
    public void delete(String id) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("id", id)
                .put("actionType", "delete")
                .create();

        ContractService.INSTANCE
                .changeContract(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.deleteSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void check(String id) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("id", id)
                .put("actionType", "update")
                .put("status", "1")
                .create();

        ContractService.INSTANCE
                .changeContract(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.checkSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
