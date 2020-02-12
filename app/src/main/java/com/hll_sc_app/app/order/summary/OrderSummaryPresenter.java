package com.hll_sc_app.app.order.summary;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.summary.OrderSummaryWrapper;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryPresenter implements IOrderSummaryContract.IOrderSummaryPresenter {
    private IOrderSummaryContract.IOrderSummaryView mView;
    private int mPageNum;

    public static OrderSummaryPresenter newInstance() {
        return new OrderSummaryPresenter();
    }
    private OrderSummaryPresenter() {
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
        Order.queryOrderSummary(mPageNum,
                mView.getSearchWords(),
                mView.getSearchId(),
                mView.getSearchType(),
                new SimpleObserver<List<OrderSummaryWrapper>>(mView, showLoading) {
                    @Override
                    public void onSuccess(List<OrderSummaryWrapper> orderSummaryWrappers) {
                        mView.setData(orderSummaryWrappers, mPageNum > 1);
                        if (CommonUtils.isEmpty(orderSummaryWrappers)) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IOrderSummaryContract.IOrderSummaryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
