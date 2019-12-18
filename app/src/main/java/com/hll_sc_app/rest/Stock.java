package com.hll_sc_app.rest;

import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderBean;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * 库存管理接口
 */
public class Stock {

    /**
     * 供应链采购单列表查询
     */
    public static void queryPurchaserOrderList(BaseMapReq req, SimpleObserver<SingleListResp<PurchaserOrderBean>> observer) {
        StockManageService.INSTANCE
                .queryPurchaserOrderList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 供应链采购单明细查询
     * @param purchaserBillID
     */
    public static void querySupplyChainPurchaserOrderDetail(String purchaserBillID, SimpleObserver<PurchaserOrderDetailResp> observer) {
        StockManageService.INSTANCE
                .querySupplyChainPurchaserOrderDetail(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("purchaserBillID", purchaserBillID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 供应链供应商列表查询
     * @param pageNo
     * @param searchKey
     * @param observer
     */
    public static void querySupplyChainGroupList(int pageNo, String searchKey, SimpleObserver<SingleListResp<PurchaserOrderSearchBean>> observer) {
        StockManageService.INSTANCE
                .querySupplyChainGroupList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("pageNo", String.valueOf(pageNo))
                        .put("pageSize", "20")
                        .put("searchKey", searchKey)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }
}
