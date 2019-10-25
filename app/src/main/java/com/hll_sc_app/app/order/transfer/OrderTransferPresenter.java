package com.hll_sc_app.app.order.transfer;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.transfer.OrderResultResp;
import com.hll_sc_app.bean.order.transfer.TransferResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Constants;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class OrderTransferPresenter implements IOrderTransferContract.IOrderTransferPresenter {
    private IOrderTransferContract.IOrderTransferView mView;
    private int mPageNum;

    static OrderTransferPresenter newInstance() {
        return new OrderTransferPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        getTransferOrderList(true);
    }

    private void getTransferOrderList(boolean showLoading) {
        OrderParam param = mView.getOrderParam();
        Order.getPendingTransferList(mPageNum,
                param.getFormatCreateStart(Constants.UNSIGNED_YYYY_MM_DD),
                param.getFormatCreateEnd(Constants.UNSIGNED_YYYY_MM_DD),
                param.getSearchWords(),
                param.getSearchShopID(),
                param.getSearchType(),
                new SimpleObserver<TransferResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(TransferResp resp) {
                        mView.updatePendingTransferNum(resp.getUnReceiveTotal());
                        mView.setListData(resp.getRecords(), mPageNum > 1);
                        if (!CommonUtils.isEmpty(resp.getRecords())) mPageNum++;
                    }
                });
    }

    @Override
    public void register(IOrderTransferContract.IOrderTransferView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        getTransferOrderList(false);
    }

    @Override
    public void loadMore() {
        getTransferOrderList(false);
    }

    @Override
    public void getTransferOrderDetail(String subBillId) {

    }

    @Override
    public void mallOrder(List<String> ids) {
        Order.mallOrder(ids, new SimpleObserver<OrderResultResp>(mView) {
            @Override
            public void onSuccess(OrderResultResp resp) {
                mView.mallOrderSuccess();
            }
        });
    }
}
