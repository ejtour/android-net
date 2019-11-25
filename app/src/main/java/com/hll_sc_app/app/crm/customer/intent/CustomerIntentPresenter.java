package com.hll_sc_app.app.crm.customer.intent;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public class CustomerIntentPresenter implements ICustomerIntentContract.ICustomerIntentPresenter {
    private ICustomerIntentContract.ICustomerIntentView mView;
    private int mPageNum;

    private CustomerIntentPresenter() {
    }

    public static CustomerIntentPresenter newInstance() {
        return new CustomerIntentPresenter();
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
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        SimpleObserver<SingleListResp<CustomerBean>> observer = new SimpleObserver<SingleListResp<CustomerBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<CustomerBean> customerBeanSingleListResp) {
                mView.setData(customerBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(customerBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        };
        if (mView.getType() == 2) {
            Customer.queryCustomerSeas(mPageNum, mView.getSearchWords(), observer);
        } else {
            Customer.queryIntentCustomer(mView.getType() == 1, mPageNum, mView.getSearchWords(), observer);
        }
    }

    @Override
    public void register(ICustomerIntentContract.ICustomerIntentView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
