package com.hll_sc_app.app.report.orderGoods;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsParam;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsPresenter implements IOrderGoodsContract.IOrderGoodsPresenter {
    private OrderGoodsParam mParam;
    private IOrderGoodsContract.IOrderGoodsView mView;
    private int mPageNum;

    public static OrderGoodsPresenter newInstance(OrderGoodsParam param) {
        OrderGoodsPresenter presenter = new OrderGoodsPresenter();
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void getPurchaserList(String searchWords) {

    }

    @Override
    public void loadMore() {
        getOrderGoodsDetails(false);
    }

    private void getOrderGoodsDetails(boolean showLoading) {
        Date endDate = null, startDate = null;
        if (mParam.getStartDate() == null) {
            endDate = new Date();
            startDate = CalendarUtils.getDateBefore(endDate, 29);
        } else {
            startDate = mParam.getStartDate();
            endDate = mParam.getEndDate();
        }
        Report.queryOrderGoodsDetails(mParam.getShopIDs(),
                CalendarUtils.toLocalDate(startDate),
                CalendarUtils.toLocalDate(endDate),
                mPageNum, new SimpleObserver<OrderGoodsResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(OrderGoodsResp orderGoodsResp) {
                        mView.showList(orderGoodsResp.getRecords(), mPageNum > 1);
                        if (!CommonUtils.isEmpty(orderGoodsResp.getRecords())) mPageNum++;
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void start() {
        getPurchaserList("");
        mPageNum = 1;
        getOrderGoodsDetails(true);
    }

    @Override
    public void register(IOrderGoodsContract.IOrderGoodsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
