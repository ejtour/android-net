package com.hll_sc_app.app.report.refund.wait;

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
public class WaitRefundTotalInfoPresenter implements WaitRefundTotalInfoContract.IRefundTotalInfoPresenter {
    private WaitRefundTotalInfoContract.IRefundTotalInfoView mView;

    static WaitRefundTotalInfoPresenter newInstance() {
        return new WaitRefundTotalInfoPresenter();
    }

    @Override
    public void start() {
        queryWaitRefundTotalInfo(true);
    }

    @Override
    public void register(WaitRefundTotalInfoContract.IRefundTotalInfoView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryWaitRefundTotalInfo(boolean showLoading) {
        toQueryRefundTotalInfo(showLoading);
    }

    private void toQueryRefundTotalInfo(boolean showLoading) {
        //flag : 1 待退货统计
        Report.queryRefundTotal(ReportRefundSymbolEnum.wait_for_refund.getCode(), new SimpleObserver<WaitRefundTotalResp>(mView,showLoading) {
            @Override
            public void onSuccess(WaitRefundTotalResp refundTotalResp) {
                mView.showWaitRefundTotalInfo(refundTotalResp);
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

}
