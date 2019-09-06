package com.hll_sc_app.app.crm.order.list;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Constants;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/6
 */

public class CrmOrderListPresenter implements ICrmOrderListContract.ICrmOrderListPresenter {
    private ICrmOrderListContract.ICrmOrderListView mView;
    private int mPageNum;
    private OrderParam mOrderParam;

    public static CrmOrderListPresenter newInstance(OrderParam param) {
        return new CrmOrderListPresenter(param);
    }

    private CrmOrderListPresenter(OrderParam orderParam) {
        mOrderParam = orderParam;
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Order.crmQueryOrderList(mPageNum, mView.getShopID(), mView.getBillStatus(),
                mOrderParam.getFormatCreateStart(Constants.UNSIGNED_YYYY_MM_DD),
                mOrderParam.getFormatCreateEnd(Constants.UNSIGNED_YYYY_MM_DD),
                mOrderParam.getFormatExecuteStart(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mOrderParam.getFormatExecuteEnd(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mOrderParam.getFormatSignStart(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mOrderParam.getFormatSignEnd(Constants.UNSIGNED_YYYY_MM_DD_HH),
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
    public void refresh() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void export(String email) {

    }

    @Override
    public void register(ICrmOrderListContract.ICrmOrderListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
