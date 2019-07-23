package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.bean.report.req.ReportExportReq;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class Report {

    /**
     * 客户订货明细列表查询，时间间隔不可以超过30天
     *
     * @param shopIDs   门店id串，多id用半角逗号分割
     * @param startDate 开始时间 yyyyMMdd
     * @param endDate   结束时间 yyyyMMdd
     * @param pageNum   页码
     */
    public static void queryOrderGoodsDetails(String shopIDs, String startDate, String endDate, int pageNum, SimpleObserver<OrderGoodsResp> observer) {
        ReportService.INSTANCE
                .queryOrderGoodsDetails(BaseMapReq.newBuilder()
                        .put("shopIDs", shopIDs)
                        .put("startDate", startDate)
                        .put("endDate", endDate)
                        .put("pageNo", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出客户订货明细详情
     *
     * @param shopIDs   门店id串，多id用半角逗号分割
     * @param startDate 开始时间 yyyyMMdd
     * @param endDate   结束时间 yyyyMMdd
     */
    public static void exportOrderGoodsDetails(String shopIDs, String startDate, String endDate, SimpleObserver<ExportResp> observer) {
        ReportService.INSTANCE
                .exportOrderGoodsDetails(BaseMapReq.newBuilder()
                        .put("shopIDs", shopIDs)
                        .put("startDate", startDate)
                        .put("endDate", endDate)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 报表导出
     *
     * @param reqParams 查询的请求参数，不含分页数据
     * @param pv        查询的pv号
     * @param email     邮箱地址
     */
    public static void exportReport(HashMap<String, String> reqParams, String pv, String email, SimpleObserver<ExportResp> observer) {
        ReportExportReq req = new ReportExportReq();
        req.setEmail(email);
        req.setIsBindEmail(TextUtils.isEmpty(email) ? 0 : 1);
        req.setPv(pv);
        req.setReqParams(reqParams);
        ReportService.INSTANCE
                .exportReport(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
