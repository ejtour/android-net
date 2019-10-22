package com.hll_sc_app.rest;

import com.hll_sc_app.api.OtherService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.LostResp;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.bean.other.RouteDetailResp;
import com.hll_sc_app.bean.rank.OrgRankBean;
import com.hll_sc_app.bean.rank.SalesRankResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public class Other {
    /**
     * 查询配送路线列表
     *
     * @param pageNum 页码
     * @param date    日期
     */
    public static void queryRouteList(int pageNum, String date, String shopID, SimpleObserver<SingleListResp<RouteBean>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OtherService.INSTANCE
                .queryRouteList(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("date", date)
                        .put("groupID", user.getGroupID())
                        .put("salesmanID", user.getEmployeeID())
                        .put("shopIDs", shopID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查看配送路线详情
     *
     * @param pageNum    页码
     * @param deliveryNo 运输单号
     */
    public static void queryRouteDetail(int pageNum, String deliveryNo, String shopID, SimpleObserver<RouteDetailResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OtherService.INSTANCE
                .queryRouteDetail(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("groupID", user.getGroupID())
                        .put("salesmanID", user.getEmployeeID())
                        .put("deliveryNo", deliveryNo)
                        .put("shopIDs", shopID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询销售排名
     */
    public static void querySalesRank(int pageNum, int dateType, String date, SimpleObserver<SalesRankResp> observer) {
        OtherService.INSTANCE
                .querySalesRank(buildRankReq(pageNum, dateType, date))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * @param pageNum  页码
     * @param dateType 周期标志 1:日 2:周 3:月
     * @param date     yyyyMMdd dateType为1时传选定日期，dateType为2时传选定周周一的日期，dateType为3时传选定月1号的日期
     */
    private static BaseMapReq buildRankReq(int pageNum, int dateType, String date) {
        return BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(pageNum))
                .put("pageSize", "20")
                .put("dateType", String.valueOf(dateType))
                .put("startDate", date)
                .create();
    }

    /**
     * 查询商户排名
     */
    public static void queryOrgRank(boolean shop, int pageNum, int dateType, String date, SimpleObserver<SingleListResp<OrgRankBean>> observer) {
        Observable<BaseResp<SingleListResp<OrgRankBean>>> observable;
        if (shop){
            observable= OtherService.INSTANCE
                    .queryShopRank(buildRankReq(pageNum, dateType, date));
        }else {
            observable = OtherService.INSTANCE
                    .queryGroupRank(BaseMapReq.newBuilder()
                            .put("pageNum", String.valueOf(pageNum))
                            .put("pageSize", "20")
                            .put("groupID", UserConfig.getGroupID())
                            .put("dateType", String.valueOf(dateType))
                            .put("date", date)
                            .create());
        }
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询流失采购商明细
     *
     * @param date     时间 周（周一时间） 月 （每月1号）
     * @param timeType // 2：周，3：月
     */
    public static void queryLostInfo(String date, int timeType, SimpleObserver<LostResp> observer) {
        OtherService.INSTANCE
                .queryLostInfo(BaseMapReq.newBuilder()
                        .put("date", date)
                        .put("groupID", UserConfig.getGroupID())
                        .put("optype", "1") // 0 -供应商 1-采购商
                        .put("order", "1") // 0：升序，1：降序
                        .put("timeType", String.valueOf(timeType))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询不含top10的运营分析报告
     *
     * @param date     yyyyMMdd timeType为周时传周一的日期，timeType为月时传1号日期
     * @param timeType 2:周 3:月
     */
    public static void queryAnalysisInfo(String date, int timeType, SimpleObserver<AnalysisResp> observer) {
        OtherService.INSTANCE
                .queryAnalysisInfo(BaseMapReq.newBuilder()
                        .put("date", date)
                        .put("groupID", UserConfig.getGroupID())
                        .put("dataType", "2")
                        .put("timeType", String.valueOf(timeType))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * @param date     yyyyMMdd timeType为周时传周一的日期，timeType为月时传1号日期
     * @param timeType 2:周 3:月
     */
    public static void queryTopTenInfo(String date, int timeType, SimpleObserver<TopTenResp> observer) {
        OtherService.INSTANCE
                .queryTopTenInfo(BaseMapReq.newBuilder()
                        .put("date", date)
                        .put("groupID", UserConfig.getGroupID())
                        .put("type", "1") // 1:新增合作采购商门店top10 2:新增采购商门店top10 3:新增供应商top10
                        .put("timeType", String.valueOf(timeType))
                        .put("order", "1") // 0：升序，1：降序
                        .put("sortBy", "5") // 排序字段 2: 有效单量 5:有效交易额
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询商品需求
     *
     * @param pageNum 页码
     * @param status  1:未处理，2：已处理
     */
    public static void queryGoodsDemand(int pageNum, int status, SimpleObserver<SingleListResp<GoodsDemandBean>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OtherService.INSTANCE
                .queryGoodsDemand(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("supplyID", user.getGroupID())
                        .put("status", status == 0 ? "" : String.valueOf(status))
                        .put("salesmanID", UserConfig.crm() ? user.getEmployeeID() : "")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 新增商品需求
     */
    public static void addGoodsDemand(GoodsDemandReq req, SimpleObserver<Object> observer) {
        OtherService.INSTANCE
                .addGoodsDemand(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
