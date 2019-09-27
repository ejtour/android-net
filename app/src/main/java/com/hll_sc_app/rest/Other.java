package com.hll_sc_app.rest;

import com.hll_sc_app.api.OtherService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.bean.other.RouteDetailResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

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
    public static void queryRouteList(int pageNum, String date, SimpleObserver<SingleListResp<RouteBean>> observer) {
        OtherService.INSTANCE
                .queryRouteList(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("date", date)
                        .put("pageSize", "20")
                        .put("salesmanID", UserConfig.getSalesmanID())
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
    public static void queryRouteDetail(int pageNum, String deliveryNo, SimpleObserver<RouteDetailResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OtherService.INSTANCE
                .queryRouteDetail(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("groupID", user.getGroupID())
                        .put("salesmanID", user.getEmployeeID())
                        .put("deliveryNo", deliveryNo)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
