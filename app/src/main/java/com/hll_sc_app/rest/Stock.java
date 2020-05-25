package com.hll_sc_app.rest;

import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.stockmanage.DepotCategoryReq;
import com.hll_sc_app.bean.stockmanage.DepotGoodsReq;
import com.hll_sc_app.bean.stockmanage.DepotRangeReq;
import com.hll_sc_app.bean.stockmanage.DepotResp;
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

    /**
     * 查询仓库列表
     *
     * @param pageNum     页码
     * @param searchWords 搜索词
     */
    public static void queryDepotList(int pageNum, String searchWords, SimpleObserver<SingleListResp<DepotResp>> observer) {
        StockManageService.INSTANCE
                .getDepotList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("houseName", searchWords)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询仓库详情
     *
     * @param id       仓库id
     * @param observer
     */
    public static void queryDepotInfo(String id, SimpleObserver<DepotResp> observer) {
        StockManageService.INSTANCE
                .getDepotInfo(BaseMapReq.newBuilder()
                        .put("id", id)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取存储单品
     *
     * @param id          仓库id
     * @param pageNum     页码
     * @param pageSize    数量
     * @param searchWords 搜索词
     */
    public static void getDepotStoreGoods(String id, int pageNum, int pageSize, String searchWords, SimpleObserver<SingleListResp<GoodsBean>> observer) {
        StockManageService.INSTANCE
                .getDepotStoreGoods(BaseMapReq.newBuilder()
                        .put("id", id)
                        .put("pageNum", String.valueOf(pageSize == 10 ? pageNum : 1))
                        .put("pageSize", String.valueOf(pageSize))
                        .put("productName", searchWords)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 设置默认仓库
     *
     * @param id 仓库id
     */
    public static void setDefaultDepot(String id, SimpleObserver<MsgWrapper<Object>> observer) {
        StockManageService.INSTANCE
                .setDefaultDepot(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("houseID", id)
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 切换仓库状态
     *
     * @param id       仓库id
     * @param isActive 是否启用 1：启用 0：停用
     */
    public static void toggleDepotStatus(String id, int isActive, SimpleObserver<MsgWrapper<Object>> observer) {
        StockManageService.INSTANCE
                .toggleDepotStatus(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("houseID", id)
                        .put("isActive", String.valueOf(isActive))
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 删除存储单品
     *
     * @param houseID   仓库id
     * @param productID 商品id
     */
    public static void delDepotGoods(String houseID, String productID, SimpleObserver<MsgWrapper<Object>> observer) {
        StockManageService.INSTANCE
                .delDepotGoods(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("houseID", houseID)
                        .put("productID", productID)
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 设置仓库配送范围
     */
    public static void setDepotRange(DepotRangeReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        StockManageService.INSTANCE
                .setDepotRange(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 设置仓库存储分类
     */
    public static void setDepotCategory(DepotCategoryReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        StockManageService.INSTANCE
                .setDepotCategory(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 保存存储单品
     */
    public static void saveDepotGoodsList(DepotGoodsReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        StockManageService.INSTANCE
                .saveDepotGoodsList(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 保存仓库
     */
    public static void saveDepotInfo(DepotResp resp, SimpleObserver<Object> observer) {
        StockManageService.INSTANCE
                .saveDepotInfo(new BaseReq<>(resp))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
