package com.hll_sc_app.app.report.customreceivequery.detail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/***
 * 客户收货查询
 * */
public class CustomReceiveDetailPresent implements ICustomReceiveDetailContract.IPresent {
    private ICustomReceiveDetailContract.IView mView;
    private int pageSize = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;

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
    public void queryList(boolean isLoading) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(pageTempNum))
                .put("pageSize", String.valueOf(pageSize))
                .create();

        ReportService.INSTANCE
                .queryCustomReceiveList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CustomReceiveListResp>() {
                    @Override
                    public void onSuccess(CustomReceiveListResp customReceiveListResp) {
                        mView.querySuccess(customReceiveListResp.getList(), pageTempNum > 1);
                        pageNum = pageTempNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageTempNum = pageNum;
                    }
                });
    }


    @Override
    public void refresh(boolean isLoading) {
        pageTempNum = 1;
        queryList(isLoading);
    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryList(false);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

}
