package com.hll_sc_app.rest;

import com.hll_sc_app.api.InspectionService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.bean.inspection.InspectionResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public class Inspection {
    /**
     * 获取验货单列表
     *
     * @param pageNum   页码
     * @param startTime 开始时间 yyyyMMdd
     * @param endTime   结束时间 yyyyMMdd
     * @param shopIDs   门店id组，以半角逗号分隔
     */
    public static void getInspectionList(int pageNum, String startTime, String endTime, String shopIDs, SimpleObserver<InspectionResp> observer) {
        InspectionService.INSTANCE
                .getInspectionList(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("startTime", startTime)
                        .put("endTime", endTime)
                        .put("groupID", UserConfig.getGroupID())
                        .put("shopIDs", shopIDs)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取验货单详情
     *
     * @param id 验货单id
     */
    public static void getInspectionDetail(String id, SimpleObserver<InspectionBean> observer) {
        InspectionService.INSTANCE
                .getInspectionDetail(BaseMapReq.newBuilder()
                        .put("id", id)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
