package com.hll_sc_app.app.report.refund.statistic;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.enums.ReportRefundSymbolEnum;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 *
 *日销售汇总
 * @author 初坤
 * @date 2019/7/20
 */
public class RefundStatisticPresenter implements IRefundStatisticContract.IRefundStatisticPresenter {
    private IRefundStatisticContract.IRefundStatisticView mView;

    static RefundStatisticPresenter newInstance() {
        return new RefundStatisticPresenter();
    }

    @Override
    public void start() {
        queryRefundedTotalInfo(true);
    }

    @Override
    public void register(IRefundStatisticContract.IRefundStatisticView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryRefundedTotalInfo(boolean showLoading) {
        toQueryRefundTotalInfo(showLoading);
    }

    private void toQueryRefundTotalInfo(boolean showLoading) {
        //flag : 1 待退货统计
        Report.queryRefundTotal(ReportRefundSymbolEnum.refunded.getCode(), new SimpleObserver<WaitRefundTotalResp>(mView,showLoading) {
            @Override
            public void onSuccess(WaitRefundTotalResp refundTotalResp) {
                mView.showRefundedTotalInfo(refundTotalResp);
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

}
