package com.hll_sc_app.app.report.refund.statistic.details;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.refund.RefundDetailsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * 退货明细
 *
 * @author 初坤
 * @date 2019/7/20
 */
public class RefundDetailsPresenter implements IRefundDetailsContract.IRefundDetailsPresenter {

    private IRefundDetailsContract.IRefundDetailsView mView;
    private int mPageNum;

    static RefundDetailsPresenter newInstance() {
        return new RefundDetailsPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IRefundDetailsContract.IRefundDetailsView view) {
        this.mView = CommonUtils.checkNotNull(view);
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

    private void load(boolean showLoading) {
        Report.queryRefundedDetail(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<RefundDetailsResp>(mView, showLoading) {
            @Override
            public void onSuccess(RefundDetailsResp refundDetailsResp) {
                mView.setData(refundDetailsResp, mPageNum > 1);
                if (CommonUtils.isEmpty(refundDetailsResp.getGroupVoList())) return;
                mPageNum++;
            }
        });
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
                .create().getData(), "111021", email, Utils.getExportObserver(mView));
    }
}
