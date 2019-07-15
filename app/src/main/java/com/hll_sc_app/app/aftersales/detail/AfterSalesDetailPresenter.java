package com.hll_sc_app.app.aftersales.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;

import java.util.List;

public class AfterSalesDetailPresenter implements IAfterSalesDetailContract.IAfterSalesDetailPresenter {
    private IAfterSalesDetailContract.IAfterSalesDetailView mView;

    static AfterSalesDetailPresenter newInstance() {
        return new AfterSalesDetailPresenter();
    }

    private AfterSalesDetailPresenter() {
    }

    @Override
    public void getDetail(String refundBillID) {
        AfterSales.requestAfterSalesDetail(
                refundBillID,
                new SimpleObserver<List<AfterSalesBean>>(mView) {
                    @Override
                    public void onSuccess(List<AfterSalesBean> recordsBeans) {
                        if (!CommonUtils.isEmpty(recordsBeans)) {
                            mView.showDetail(recordsBeans.get(0));
                        } else {
                            mView.showToast("获取数据失败");
                        }
                    }
                });
    }

    @Override
    public void doAction(int actionType, String payType, String billID, int status, int type, String msg) {
        AfterSales.afterSalesAction(actionType, billID, status, type, payType, msg, null, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.handleStatusChange();
            }
        });
    }

    @Override
    public void modifyPrice(String price, String refundBillDetailID, String refundBillID) {
        AfterSales.modifyUnitPrice(price, refundBillDetailID, refundBillID, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.handleStatusChange();
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(IAfterSalesDetailContract.IAfterSalesDetailView view) {
        mView = CommonUtils.checkNotNull(view);
    }
}
