package com.hll_sc_app.app.crm.customer.intent.add;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public class AddCustomerPresenter implements IAddCustomerContract.IAddCustomerPresenter {
    private IAddCustomerContract.IAddCustomerView mView;

    private AddCustomerPresenter() {
    }

    public static AddCustomerPresenter newInstance() {
        return new AddCustomerPresenter();
    }

    @Override
    public void save(CustomerBean bean) {
        Customer.saveIntentCustomer(bean, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void register(IAddCustomerContract.IAddCustomerView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
