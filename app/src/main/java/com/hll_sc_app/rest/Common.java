package com.hll_sc_app.rest;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.event.ShopSearchEvent;
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
                .queryCooperationShop(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 请求搜索门店
     *
     * @param searchWords 搜索词
     */
    public static void searchShopList(String searchWords, SimpleObserver<SingleListResp<ShopSearchEvent>> observer) {
        orgSearch(searchWords, 1, "", observer);
    }

    public static void searchGroupList(String searchWords, SimpleObserver<SingleListResp<ShopSearchEvent>> observer) {
        orgSearch(searchWords, 0, "", observer);
    }

    public static void searchShopList(String searchWords, String purchaserID, SimpleObserver<SingleListResp<ShopSearchEvent>> observer) {
        orgSearch(searchWords, 1, purchaserID, observer);
    }

    private static void orgSearch(String searchWords, int type, String purchaserID, SimpleObserver<SingleListResp<ShopSearchEvent>> observer) {
        CommonService.INSTANCE
                .searchShopList(BaseMapReq.newBuilder()
                        .put("searchWords", searchWords)
                        .put("source", "0")
                        .put("type", String.valueOf(type))
                        .put("purchaserID", purchaserID)
                        .put("shopMallID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }

    /**
     * 搜索意向客户
     *
     * @param pageNum     页码
     * @param searchWords 搜索词
     */
    public static void searchIntentionCustomer(int pageNum, String searchWords, SimpleObserver<SingleListResp<CustomerBean>> observer) {
        CommonService.INSTANCE
                .searchIntentionCustomer(BaseMapReq.newBuilder()
                        .put("customerName", searchWords)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 搜索货主列表
     *
     * @param actionType 0-代仓公司名称,1-货主名称,2-根据货主名称查询代仓公司
     * @param status 0，查待同意，1查未同意，2查已同意，3查所有 不传默认查所有
     * @param searchWord 搜索词
     */
    public static void searchShipperList(int actionType, String searchWord, String status, SimpleObserver<List<WareHouseShipperBean>> observer) {
        CommonService.INSTANCE
                .searchShipperList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("actionType", String.valueOf(actionType))
                        .put("name", searchWord)
                        .put("isSizeLimit", "1")
                        .put("status", status)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }
}
