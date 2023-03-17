package com.hll_sc_app.app.report.refund.wait.product;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.refund.RefundProductResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * 待退商品明细
 *
 * @author 初坤
 * @date 2019/7/20
 */
public class WaitRefundProductPresenter implements WaitRefundProductContract.IWaitRefundProductPresenter {
    private WaitRefundProductContract.IWaitRefundProductView mView;
    private int mPageNum;

    static WaitRefundProductPresenter newInstance() {
        return new WaitRefundProductPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(WaitRefundProductContract.IWaitRefundProductView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    private void load(boolean showLoading) {
        Report.queryWaitRefundProductList(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<RefundProductResp>(mView, showLoading) {
            @Override
            public void onSuccess(RefundProductResp refundProductResp) {
                mView.setData(refundProductResp, mPageNum > 1);
                if (CommonUtils.isEmpty(refundProductResp.getRecords())) return;
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
                .put("pageSize", "").create().getData(), "111015", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }
}
