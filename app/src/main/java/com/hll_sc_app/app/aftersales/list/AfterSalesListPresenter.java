package com.hll_sc_app.app.aftersales.list;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/10
 */

public class AfterSalesListPresenter implements IAfterSalesListContract.IAfterSalesListPresenter {
    private int mPageNum = 2;
    private String mBillID;
    private IAfterSalesListContract.IAfterSalesListView mView;

    static AfterSalesListPresenter newInstance(String billID) {
        return new AfterSalesListPresenter(billID);
    }

    private AfterSalesListPresenter(String billID) {
        mBillID = billID;
    }

    private void load(boolean showLoading) {
        Order.queryAssociatedAfterSalesOrder(mPageNum, mBillID, new SimpleObserver<SingleListResp<AfterSalesBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<AfterSalesBean> afterSalesBeanSingleListResp) {
                mView.setData(afterSalesBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(afterSalesBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
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
    public void register(IAfterSalesListContract.IAfterSalesListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
