package com.hll_sc_app.app.crm.customer;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.customer.CrmCustomerResp;
import com.hll_sc_app.bean.customer.CrmShopResp;
import com.hll_sc_app.bean.home.VisitResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;
import com.hll_sc_app.rest.Home;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/15
 */

public class CrmCustomerPresenter implements ICrmCustomerContract.ICrmCustomerPresenter {
    private ICrmCustomerContract.ICrmCustomerView mView;

    @Override
    public void start() {
        Customer.queryCustomerInfo(new SimpleObserver<CrmCustomerResp>(mView) {
            @Override
            public void onSuccess(CrmCustomerResp crmCustomerResp) {
                mView.setCustomerInfo(crmCustomerResp);
            }
        });
        Customer.queryShopInfo(new SimpleObserver<CrmShopResp>(mView) {
            @Override
            public void onSuccess(CrmShopResp crmShopResp) {
                mView.setShopInfo(crmShopResp);
            }
        });
        Home.queryVisitPlan(new SimpleObserver<VisitResp>(mView) {
            @Override
            public void onSuccess(VisitResp resp) {
                mView.setVisitInfo(resp);
            }
        });
    }

    @Override
    public void register(ICrmCustomerContract.ICrmCustomerView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
