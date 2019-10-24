package com.hll_sc_app.app.report.customreceivequery.detail;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/***
 * 客户收货查询
 * */
public class CustomReceiveDetailPresent implements ICustomReceiveDetailContract.IPresent {
    private ICustomReceiveDetailContract.IView mView;

    public static CustomReceiveDetailPresent newInstance() {
        return new CustomReceiveDetailPresent();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(ICustomReceiveDetailContract.IView view) {
        mView = view;
    }


    @Override
    public void queryDetail() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("groupID", mView.getOwnerId())
                .put("voucherID", mView.getVoucherId())
                .create();

        ReportService.INSTANCE
                .queryCustomReceiveDetail(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                        mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<CustomReceiveDetailBean>>() {
                    @Override
                    public void onSuccess(List<CustomReceiveDetailBean> customReceiveDetailBeans) {
                        mView.querySuccess(customReceiveDetailBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


}
