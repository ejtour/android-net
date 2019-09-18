package com.hll_sc_app.app.stockmanage.purchaserorder.detail;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

/**
 *
 * 采购单详情查询
 * @author 初坤
 * @date 2019/7/20
 */
public class PurchaserOrderDetailPresenter implements PurchaserOrderDetailContract.IPurchaserOrderDetailPresenter {
    private PurchaserOrderDetailContract.IPurchaserOrderDetailView mView;

    static PurchaserOrderDetailPresenter newInstance() {
        return new PurchaserOrderDetailPresenter();
    }

    @Override
    public void start() {
        queryPurchaserOrderDetail(true);
    }

    @Override
    public void register(PurchaserOrderDetailContract.IPurchaserOrderDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserOrderDetail(boolean showLoading) {
        toPurchaserOrderList(showLoading);
    }

    private void toPurchaserOrderList(boolean showLoading) {
        String purchaserBillID = mView.getPurchaserBillID();
        Stock.querySupplyChainPurchaserOrderDetail(UserConfig.getGroupID(),purchaserBillID, new SimpleObserver<PurchaserOrderDetailResp>(mView,showLoading) {
            @Override
            public void onSuccess(PurchaserOrderDetailResp purchaserOrderResp) {
                mView.showPurchaserOrderDetail(purchaserOrderResp);
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

}
