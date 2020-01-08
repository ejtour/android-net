package com.hll_sc_app.rest;

import com.hll_sc_app.api.HomeService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.hll_sc_app.bean.home.ManagementShopResp;
import com.hll_sc_app.bean.home.StatisticResp;
import com.hll_sc_app.bean.home.VisitResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public class Home {
    /**
     * 首页查询销售额
     *
     * @param date 标识：0-日,1-周,2-月
     */
    public static void querySalesVolume(int date, SimpleObserver<SalesVolumeResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        HomeService.INSTANCE
                .querySalesVolume(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("date", String.valueOf(date))
                        .put("roleTypes", user.getAuthType())
                        .put("version", "1").create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取管理门店数
     */
    public static void queryManagementShopInfo(SimpleObserver<ManagementShopResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        HomeService.INSTANCE
                .getManagementShop(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("salesmanID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取 CRM 首页统计与趋势图
     *
     * @param actionType 0-首页统计，2-趋势图
     */
    public static void queryCrmHomeStatistic(int actionType, SimpleObserver<StatisticResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        HomeService.INSTANCE
                .getStatistic(BaseMapReq.newBuilder()
                        .put("actionType", String.valueOf(actionType))
                        .put("groupID", user.getGroupID())
                        .put("salesmanID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取 CRM 拜访计划
     */
    public static void queryVisitPlan(SimpleObserver<VisitResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        HomeService.INSTANCE
                .getVisit(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("employeeID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
