package com.hll_sc_app.rest;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class Common {
    /**
     * 搜索合作采购商列表
     *
     * @param actionType  操作类型
     * @param searchWords 搜索词
     */
    public static void queryPurchaserList(String actionType, String searchWords, SimpleObserver<List<PurchaserBean>> observer) {
        CommonService.INSTANCE
                .queryPurchaserList(BaseMapReq.newBuilder()
                        .put("actionType", actionType)
                        .put("groupID", UserConfig.getGroupID())
                        .put("searchParam", searchWords).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 搜索合作采购商门店列表
     *
     * @param purchaserID 采购商 id
     * @param actionType  操作类型
     * @param searchWords 搜索词
     */
    public static void queryPurchaserShopList(String purchaserID, String actionType, String searchWords, SimpleObserver<List<PurchaserShopBean>> observer) {
        CommonService.INSTANCE
                .queryPurchaserShopList(BaseMapReq.newBuilder()
                        .put("actionType", actionType)
                        .put("purchaserID", purchaserID)
                        .put("groupID", UserConfig.getGroupID())
                        .put("searchParam", searchWords).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /**
     * 通用表格导出
     *
     * @param req 明细请求
     */
    public static void exportExcel(ExportReq req, SimpleObserver<ExportResp> observer) {
        CommonService.INSTANCE
                .exportExcel(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询合作关系店铺列表
     */
    public static void queryCooperationShop(BaseMapReq req, SimpleObserver<CooperationShopListResp> observer) {
        CommonService.INSTANCE
                .listCooperationShop(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
