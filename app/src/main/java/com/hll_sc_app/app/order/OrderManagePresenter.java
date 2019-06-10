package com.hll_sc_app.app.order;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Constants;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */
public class OrderManagePresenter implements IOrderManageContract.IOrderManagePresenter {
    private IOrderManageContract.IOrderManageView mView;
    private int mPageNum;

    private OrderManagePresenter() {
    }

    static OrderManagePresenter newInstance() {
        return new OrderManagePresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        getOrderList(true);
    }

    private void getOrderList(boolean showLoading) {
        OrderParam param = mView.getOrderParam();
        if (mView.getOrderStatus() > 0) { // 如果不是待转单
            Order.getOrderList(mPageNum,
                    param.getFlag(),
                    mView.getOrderStatus(),
                    param.getSearchWords(),
                    param.getFormatCreateStart(Constants.FORMAT_YYYY_MM_DD),
                    param.getFormatCreateEnd(Constants.FORMAT_YYYY_MM_DD),
                    param.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_HH),
                    param.getFormatExecuteEnd(Constants.FORMAT_YYYY_MM_DD_HH),
                    param.getFormatSignStart(Constants.FORMAT_YYYY_MM_DD_HH),
                    param.getFormatSignEnd(Constants.FORMAT_YYYY_MM_DD_HH),
                    mView.getDeliverType(),
                    new SimpleObserver<List<OrderResp>>(mView, showLoading) {
                        @Override
                        public void onSuccess(List<OrderResp> orderResps) {
                            if (mPageNum == 1) {
                                mView.refreshListData(orderResps);
                            } else {
                                mView.appendListData(orderResps);
                            }
                            if (!CommonUtils.isEmpty(orderResps)) {
                                mPageNum++;
                            }
                        }
                    });
        }
    }

    @Override
    public void register(IOrderManageContract.IOrderManageView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        getOrderList(false);
    }

    @Override
    public void loadMore() {
        getOrderList(false);
    }

    @Override
    public void receiveOrder(String subBillIds) {
        Order.modifyOrderStatus(1, subBillIds, 0,
                null, null, null,
                new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.showToast("成功接单");
                        mView.statusChanged();
                    }
                });
    }

    @Override
    public void deliver(String subBillIds, String expressName, String expressNo) {
        Order.modifyOrderStatus(2, subBillIds, 0,
                null, expressName, expressNo,
                new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.showToast("成功发货");
                        mView.statusChanged();
                    }
                });
    }
}
