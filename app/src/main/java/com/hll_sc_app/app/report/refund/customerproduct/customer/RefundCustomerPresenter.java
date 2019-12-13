package com.hll_sc_app.app.report.refund.customerproduct.customer;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.refund.RefundCustomerResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/13
 */

public class RefundCustomerPresenter implements IRefundCustomerContract.IRefundCustomerPresenter {
    private IRefundCustomerContract.IRefundCustomerView mView;
    private int mPageNum;

    private RefundCustomerPresenter() {
    }

    public static RefundCustomerPresenter newInstance() {
        return new RefundCustomerPresenter();
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
                .put("pageSize", "").create().getData(), "111020", email, Utils.getExportObserver(mView));
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Report.queryRefundedCustomerList(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<RefundCustomerResp>(mView, showLoading) {
            @Override
            public void onSuccess(RefundCustomerResp refundCustomerResp) {
                mView.setData(refundCustomerResp, mPageNum > 1);
                if (CommonUtils.isEmpty(refundCustomerResp.getGroupVoList())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(IRefundCustomerContract.IRefundCustomerView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
