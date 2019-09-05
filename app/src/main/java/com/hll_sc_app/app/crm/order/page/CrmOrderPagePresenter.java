package com.hll_sc_app.app.crm.order.page;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.filter.CrmOrderParam;
import com.hll_sc_app.bean.order.shop.OrderShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Constants;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public class CrmOrderPagePresenter implements ICrmOrderPageContract.ICrmOrderPagePresenter {
    private CrmOrderParam mParam;
    private int mBillStatus;
    private int mPageNum;
    private ICrmOrderPageContract.ICrmOrderPageView mView;

    public static CrmOrderPagePresenter newInstance(CrmOrderParam param, int billStatus) {
        return new CrmOrderPagePresenter(param, billStatus);
    }

    private CrmOrderPagePresenter(CrmOrderParam param, int billStatus) {
        mParam = param;
        mBillStatus = billStatus;
    }

    private void load(boolean showLoading) {
        Order.queryOrderShopList(
                mBillStatus,
                mParam.getActionType(),
                mPageNum,
                mParam.getSearchWords(),
                mParam.getFormatCreateStart(Constants.UNSIGNED_YYYY_MM_DD),
                mParam.getFormatCreateEnd(Constants.UNSIGNED_YYYY_MM_DD),
                mParam.getFormatExecuteStart(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mParam.getFormatExecuteEnd(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mParam.getFormatSignStart(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mParam.getFormatSignEnd(Constants.UNSIGNED_YYYY_MM_DD_HH),
                new SimpleObserver<OrderShopResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(OrderShopResp orderShopResp) {
                        mView.setShopListData(orderShopResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(orderShopResp.getOrders())) return;
                        mPageNum++;
                    }
                });
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

    @Override
    public void register(ICrmOrderPageContract.ICrmOrderPageView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
