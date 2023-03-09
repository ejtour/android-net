package com.hll_sc_app.app.report.deliverytime.detail;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author chukun
 * @since 2019/8/15
 */

public class DeliveryTimeDetailPresenter implements IDeliveryTimeDetailContract.IDeliveryTimeDetailPresenter {

    private int mPageNum;
    private IDeliveryTimeDetailContract.IDeliveryTimeDetailView mView;

    public static DeliveryTimeDetailPresenter newInstance() {
        return new DeliveryTimeDetailPresenter();
    }

    private DeliveryTimeDetailPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Report.queryDeliveryTimeContent(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<DeliveryTimeResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(DeliveryTimeResp deliveryTimeResp) {
                        mView.setData(deliveryTimeResp, mPageNum > 1);
                        if (!CommonUtils.isEmpty(deliveryTimeResp.getRecords())) {
                            mPageNum++;
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            User.bindEmail(email, new SimpleObserver<Object>(mView) {
                @Override
                public void onSuccess(Object o) {
                    export(null);
                }
            });
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111008", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void register(IDeliveryTimeDetailContract.IDeliveryTimeDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
