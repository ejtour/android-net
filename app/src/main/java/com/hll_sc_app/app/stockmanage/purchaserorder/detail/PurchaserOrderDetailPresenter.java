package com.hll_sc_app.app.stockmanage.purchaserorder.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

/**
 * 采购单详情查询
 *
 * @author 初坤
 * @date 2019/7/20
 */
public class PurchaserOrderDetailPresenter implements IPurchaserOrderDetailContract.IPurchaserOrderDetailPresenter {
    private IPurchaserOrderDetailContract.IPurchaserOrderDetailView mView;

    static PurchaserOrderDetailPresenter newInstance() {
        return new PurchaserOrderDetailPresenter();
    }

    private PurchaserOrderDetailPresenter() {
    }

    @Override
    public void start() {
        Stock.querySupplyChainPurchaserOrderDetail(mView.getPurchaserBillID(), new SimpleObserver<PurchaserOrderDetailResp>(mView) {
            @Override
            public void onSuccess(PurchaserOrderDetailResp purchaserOrderResp) {
                mView.setData(purchaserOrderResp);
            }
        });
    }

    @Override
    public void register(IPurchaserOrderDetailContract.IPurchaserOrderDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
