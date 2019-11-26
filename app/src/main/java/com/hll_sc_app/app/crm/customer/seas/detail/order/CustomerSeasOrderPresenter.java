package com.hll_sc_app.app.crm.customer.seas.detail.order;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerSeasOrderPresenter implements ICustomerSeasOrderContract.ICustomerSeasOrderPresenter {
    private ICustomerSeasOrderContract.ICustomerSeasOrderView mView;
    private int mPageNum;

    private CustomerSeasOrderPresenter() {
    }

    public static CustomerSeasOrderPresenter newInstance() {
        return new CustomerSeasOrderPresenter();
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
        Order.crmQueryOrderList(mPageNum, mView.getShopID(), 0,
                null, null, null, null, null, null,
                new SimpleObserver<SingleListResp<OrderResp>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<OrderResp> orderRespSingleListResp) {
                        mView.setData(orderRespSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(orderRespSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(ICustomerSeasOrderContract.ICustomerSeasOrderView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
