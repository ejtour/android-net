package com.hll_sc_app.app.report.customersettle.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

class CustomerSettleDetailPresenter implements ICustomerSettleDetailContract.ICustomerSettleDetailPresenter {
    private ICustomerSettleDetailContract.ICustomerSettleDetailView mView;

    public static CustomerSettleDetailPresenter newInstance() {
        return new CustomerSettleDetailPresenter();
    }

    private CustomerSettleDetailPresenter() {
    }

    @Override
    public void start() {
        Report.queryCustomerSettleDetail(mView.getReq().create(), new SimpleObserver<CustomerSettleDetailResp>(mView) {
            @Override
            public void onSuccess(CustomerSettleDetailResp customerSettleDetailResp) {
                mView.setData(customerSettleDetailResp);
            }
        });
    }

    @Override
    public void register(ICustomerSettleDetailContract.ICustomerSettleDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
