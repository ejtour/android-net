package com.hll_sc_app.app.report.refund.wait.customer;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.refund.RefundCustomerResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 *
 * 待退商品明细
 * @author 初坤
 * @date 2019/7/20
 */
public class WaitRefundCustomerPresenter implements IWaitRefundCustomerContract.IWaitRefundCustomerPresenter {

    private IWaitRefundCustomerContract.IWaitRefundCustomerView mView;
    private int mPageNum;

    static WaitRefundCustomerPresenter newInstance() {
        return new WaitRefundCustomerPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IWaitRefundCustomerContract.IWaitRefundCustomerView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    private void load(boolean showLoading) {
        Report.queryWaitRefundCustomerList(mView.getReq()
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
                .put("pageSize", "").create().getData(), "111014", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }
}
