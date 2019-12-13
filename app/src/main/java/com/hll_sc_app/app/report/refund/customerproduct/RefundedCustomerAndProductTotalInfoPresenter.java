package com.hll_sc_app.app.report.refund.customerproduct;

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
public class RefundedCustomerAndProductTotalInfoPresenter implements RefundedCustomerAndProductTotalInfoContract.IRefundedCustomerAndProductTotalInfoPresenter {
    private RefundedCustomerAndProductTotalInfoContract.IRefundedCustomerAndProductTotalInfoView mView;

    static RefundedCustomerAndProductTotalInfoPresenter newInstance() {
        return new RefundedCustomerAndProductTotalInfoPresenter();
    }

    @Override
    public void start() {
        queryRefundedCustomerAndProductTotalInfo(true);
    }

    @Override
    public void register(RefundedCustomerAndProductTotalInfoContract.IRefundedCustomerAndProductTotalInfoView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryRefundedCustomerAndProductTotalInfo(boolean showLoading) {
        toRefundedCustomerAndProductTotalInfo(showLoading);
    }

    private void toRefundedCustomerAndProductTotalInfo(boolean showLoading) {
        //flag : 3 退货商品与客户
        Report.queryRefundTotal(ReportRefundSymbolEnum.refund_customer.getCode(), new SimpleObserver<WaitRefundTotalResp>(mView,showLoading) {
            @Override
            public void onSuccess(WaitRefundTotalResp refundTotalResp) {
                mView.showRefundedCustomerAndProductTotalInfo(refundTotalResp);
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

}
