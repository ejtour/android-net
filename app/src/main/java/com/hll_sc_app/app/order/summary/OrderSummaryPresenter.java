package com.hll_sc_app.app.order.summary;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

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
                new SimpleObserver<SingleListResp<SummaryPurchaserBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<SummaryPurchaserBean> summaryPurchaserBeanSingleListResp) {
                        mView.setData(summaryPurchaserBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(summaryPurchaserBeanSingleListResp.getRecords()))
                            return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IOrderSummaryContract.IOrderSummaryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
