package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.bean.report.req.ReportExportReq;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
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
    public static void queryOrderGoods(String shopIDs, String startDate, String endDate, int pageNum, SimpleObserver<OrderGoodsResp<OrderGoodsBean>> observer) {
        ReportService.INSTANCE
                .queryOrderGoods(BaseMapReq.newBuilder()
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
    public static void exportReport( String reqParams, String pv, String email, SimpleObserver<ExportResp> observer) {
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

    /**
     * 查询订货门店详情
     *
     * @param shopID    门店 id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param pageNum   页码
     */
    public static void queryOrderGoodsDetail(String shopID, String startDate, String endDate, int pageNum, SimpleObserver<OrderGoodsResp<OrderGoodsDetailBean>> observer) {
        ReportService.INSTANCE
                .queryOrderGoodsDetail(BaseMapReq.newBuilder()
                        .put("shopID", shopID)
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
     * 查询商品销售统计汇总
     *
     * @param dateFlag  日期标志 1:本周 2:本月 3:上月 4:自定义
     * @param startDate 结束日期（yyyyMMdd，dateFlag为4时，此字段不可为空）
     * @param endDate   开始日期（yyyyMMdd，dateFlag为4时，此字段不可为空）
     */
    public static void queryProductSales(int dateFlag, String startDate, String endDate, SimpleObserver<ProductSaleResp> observer) {
        ReportService.INSTANCE
                .queryProductSales(BaseMapReq.newBuilder()
                        .put("dateFlag", String.valueOf(dateFlag))
                        .put("startDate", startDate)
                        .put("endDate", endDate)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询商品销售TOP10
     *
     * @param dateFlag  日期标志 1:本周 2:本月 3:上月 4:自定义
     * @param startDate 结束日期（yyyyMMdd，dateFlag为4时，此字段不可为空）
     * @param endDate   开始日期（yyyyMMdd，dateFlag为4时，此字段不可为空）
     * @param type      top10类型 1:销量 2: 金额
     */
    public static void queryProductSalesTop10(int dateFlag, String startDate, String endDate, int type, SimpleObserver<ProductSaleTop10Resp> observer) {
        ReportService.INSTANCE
                .queryProductSalesTop10(BaseMapReq.newBuilder()
                        .put("dateFlag", String.valueOf(dateFlag))
                        .put("startDate", startDate)
                        .put("endDate", endDate)
                        .put("type", String.valueOf(type))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 客户缺货统计表
     * @param requestParams  查询参数
     */
    public static void queryCustomerLack(CustomerLackReq requestParams, SimpleObserver<CustomerLackResp> observer) {
        ReportService.INSTANCE
                .queryCustomerLack(new BaseReq<>(requestParams))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
