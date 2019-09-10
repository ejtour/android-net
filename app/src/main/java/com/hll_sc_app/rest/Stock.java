package com.hll_sc_app.rest;

import com.hll_sc_app.api.StockService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * 库存管理接口
 */
public class Stock {

    /**
     * 供应链采购单列表查询
     * @param orderReq
     */
    public static void querySupplyChainPurchaserOrderList(PurchaserOrderReq orderReq, SimpleObserver<PurchaserOrderResp> observer) {
        StockService.INSTANCE
                .querySupplyChainPurchaserOrderList(new BaseReq<>(orderReq))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 供应链采购单明细查询
     * @param groupID
     * @param purchaserBillID
     */
    public static void querySupplyChainPurchaserOrderDetail(String groupID,String purchaserBillID, SimpleObserver<PurchaserOrderDetailResp> observer) {
        StockService.INSTANCE
                .querySupplyChainPurchaserOrderDetail(BaseMapReq.newBuilder()
                        .put("groupID", groupID)
                        .put("purchaserBillID", purchaserBillID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
