package com.hll_sc_app.app.report.customreceivequery.detail;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;

import java.util.List;

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
        load(true);
    }

    @Override
    public void register(ICustomReceiveDetailContract.IView view) {
        mView = view;
    }

    @Override
    public void refresh() {
        load(false);
    }

    private void load(boolean showLoading) {
        SimpleObserver<List<CustomReceiveDetailBean>> observer = new SimpleObserver<List<CustomReceiveDetailBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<CustomReceiveDetailBean> customReceiveDetailBeans) {
                mView.querySuccess(customReceiveDetailBeans);
            }
        };
        ReportService.INSTANCE
                .queryCustomReceiveDetail(BaseMapReq.newBuilder()
                        .put("groupID", mView.getOwnerId())
                        .put("voucherID", mView.getVoucherId())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }
}
