package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.credit.CreditDetailsResp;
import com.hll_sc_app.bean.report.daily.SalesDailyBean;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.lack.CustomerLackResp;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.bean.report.lack.LackDiffResp;
import com.hll_sc_app.bean.report.loss.LossBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsResp;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.bean.report.produce.ProduceInputReq;
import com.hll_sc_app.bean.report.produce.ProduceSummaryResp;
import com.hll_sc_app.bean.report.profit.ProfitResp;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.bean.report.purchase.ManHourReq;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;
import com.hll_sc_app.bean.report.receive.ReceiveDiffDetailsResp;
import com.hll_sc_app.bean.report.receive.ReceiveDiffResp;
import com.hll_sc_app.bean.report.refund.RefundCustomerResp;
import com.hll_sc_app.bean.report.refund.RefundDetailsResp;
import com.hll_sc_app.bean.report.refund.RefundProductResp;
import com.hll_sc_app.bean.report.refund.RefundReasonBean;
import com.hll_sc_app.bean.report.refund.RefundResp;
import com.hll_sc_app.bean.report.req.ReportExportReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.bean.report.search.SearchReq;
import com.hll_sc_app.bean.report.search.SearchResultResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseFeeBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

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
    public static <T> void exportReport(T reqParams, String pv, String email, SimpleObserver<ExportResp> observer) {
        ReportExportReq<T> req = new ReportExportReq<T>();
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
     *
     * @param req 查询参数
     */
    public static void queryCustomerLack(BaseMapReq req, SimpleObserver<CustomerLackResp> observer) {
        ReportService.INSTANCE
                .queryCustomerLack(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 收货差异汇总
     *
     * @param observer
     */
    public static void queryReceiveDiff(BaseMapReq req, SimpleObserver<ReceiveDiffResp> observer) {
        ReportService.INSTANCE
                .queryReceiveDiff(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 收货差异明细
     *
     * @param observer
     */
    public static void queryReceiveDiffDetails(BaseMapReq req, SimpleObserver<ReceiveDiffDetailsResp> observer) {
        ReportService.INSTANCE
                .queryReceiveDiffDetails(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 配送及时率查询
     *
     * @param observer
     */
    public static void queryDeliveryTimeContent(BaseMapReq req, SimpleObserver<DeliveryTimeResp> observer) {
        ReportService.INSTANCE.queryDeliveryTime(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询采购汇总
     */
    public static void queryPurchaseSummary(BaseMapReq req, SimpleObserver<PurchaseSummaryResp> observer) {
        ReportService.INSTANCE
                .queryPurchaseSummary(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 录入采购信息
     *
     * @param builder 采购信息请求
     */
    public static void recordPurchaseInfo(BaseMapReq.Builder builder, SimpleObserver<MsgWrapper<Object>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        ReportService
                .INSTANCE
                .recordPurchaseInfo(builder
                        .put("groupID", user.getGroupID())
                        .put("enterEmp", user.getEmployeeName())
                        .put("currentDay", CalendarUtils.toLocalDate(new Date()))
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询生产汇总
     */
    public static void queryProduceSummary(BaseMapReq req, SimpleObserver<ProduceSummaryResp> observer) {
        ReportService.INSTANCE
                .queryProduceSummary(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 退款合计
     *
     * @param flag 1、待退统计 2、退货统计 3、退货客户统计 4、退货商品统计
     * @param observer
     */
    public static void queryRefundInfo(int flag, SimpleObserver<RefundResp> observer) {
        ReportService.INSTANCE.queryRefundInfo(BaseMapReq.newBuilder()
                .put("flag", String.valueOf(flag))
                .put("groupID", UserConfig.getGroupID())
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 待退货退款 集团列表查询
     *
     * @param observer
     */
    public static void queryWaitRefundCustomerList(BaseMapReq req, SimpleObserver<RefundCustomerResp> observer) {
        ReportService.INSTANCE.queryRefundCustomerList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 待退货 商品列表查询
     *
     * @param observer
     */
    public static void queryWaitRefundProductList(BaseMapReq req, SimpleObserver<RefundProductResp> observer) {
        ReportService.INSTANCE.queryRefundProductList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 退货明细
     *
     * @param req
     * @param observer
     */
    public static void queryRefundedDetail(BaseMapReq req, SimpleObserver<RefundDetailsResp> observer) {
        ReportService.INSTANCE.queryRefundedDetail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 退货 商品列表查询
     *
     * @param req
     * @param observer
     */
    public static void queryRefundedProductList(BaseMapReq req, SimpleObserver<RefundProductResp> observer) {
        ReportService.INSTANCE.queryRefundProductDetail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 退货客户明细
     *
     * @param req
     * @param observer
     */
    public static void queryRefundedCustomerList(BaseMapReq req, SimpleObserver<RefundCustomerResp> observer) {
        ReportService.INSTANCE.queryRefundedCustomerDetail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询工时费与班次
     *
     * @param key 1-工时费 2-班次
     */
    public static void queryManHour(int key, SimpleObserver<List<ManHourBean>> observer) {
        ReportService.INSTANCE
                .queryManHour(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("key", String.valueOf(key))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);

    }

    /**
     * 保存工时费与班次信息
     */
    public static void saveManHour(ManHourReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        ReportService.INSTANCE
                .saveManHour(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 生产汇总明细查询
     *
     * @param classes 班次
     * @param date    时间 yyyyMMdd
     * @param opType  0: 查看明细数据 1:修改明细数据
     */
    public static void queryProduceDetails(String classes, String date, int opType, SimpleObserver<List<ProduceDetailBean>> observer) {
        ReportService.INSTANCE
                .queryProduceDetails(BaseMapReq.newBuilder()
                        .put("classes", classes, true)
                        .put("date", date)
                        .put("groupID", UserConfig.getGroupID())
                        .put("opType", String.valueOf(opType))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 录入生产数据
     */
    public static void recordProduceInfo(ProduceInputReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        ReportService.INSTANCE
                .recordProduceInfo(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 录入人效
     *
     * @param date                  日期
     * @param deliveryPackageQty    包裹数量
     * @param orderQtyPackageWeight 称重数量
     */
    public static void recordPeopleEffect(String date, String deliveryPackageQty, String orderQtyPackageWeight, SimpleObserver<MsgWrapper<Object>> observer) {
        ReportService.INSTANCE
                .recordPeopleEffect(BaseMapReq.newBuilder()
                        .put("date", date)
                        .put("deliveryPackageQty", deliveryPackageQty)
                        .put("orderQtyPackageWeight", orderQtyPackageWeight)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 客户，门店流失率明细
     *
     * @param req
     * @param observer
     */
    public static void queryLossInfo(BaseMapReq req, SimpleObserver<SingleListResp<LossBean>> observer) {
        ReportService.INSTANCE.queryLossInfo(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 代仓发货统计
     *
     * @param req
     * @param observer
     */
    public static void queryWareHouseDelivery(BaseMapReq req, SimpleObserver<SingleListResp<WareHouseDeliveryBean>> observer) {
        ReportService.INSTANCE.queryWareHouseDelivery(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 代仓服务费统计
     *
     * @param req
     * @param observer
     */
    public static void queryWareHouseFee(BaseMapReq req, SimpleObserver<SingleListResp<WareHouseFeeBean>> observer) {
        ReportService.INSTANCE.queryWareHouseFee(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 搜索查询
     *
     * @param req
     * @param observer
     */
    public static void querySearchList(SearchReq req, SimpleObserver<SearchResultResp> observer) {
        ReportService.INSTANCE.querySearchList(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 销售日报
     *
     * @param req
     * @param observer
     */
    public static void querySalesDaily(BaseMapReq req, SimpleObserver<SingleListResp<SalesDailyBean>> observer) {
        ReportService.INSTANCE.querySalesDaily(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /**
     * 日销售额查询
     *
     * @param req
     * @return
     */
    public static void queryDateSaleAmount(BaseMapReq req, SimpleObserver<DateSaleAmountResp> observer) {
        ReportService.INSTANCE
                .queryDateSaleAmount(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 客户销售/门店汇总查询
     *
     * @param req
     * @return
     */
    public static void queryCustomerSales(BaseMapReq req, SimpleObserver<CustomerSalesResp> observer) {
        ReportService.INSTANCE
                .queryCustomerSales(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询客户签约绩效
     *
     * @param req
     * @param observer
     */
    public static void querySalesmanSignAchievement(BaseMapReq req,
                                                    SimpleObserver<SalesManSignResp> observer) {
        ReportService.INSTANCE
                .querySalesManSignAchievement(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询客户签约绩效
     *
     * @param req
     * @param observer
     */
    public static void querySalesmanSalesAchievement(BaseMapReq req,
                                                     SimpleObserver<SalesManSalesResp> observer) {
        ReportService.INSTANCE
                .querySalesManSalesAchievement(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询缺货汇总
     *
     * @param req
     * @param observer
     */
    public static void queryLackDiff(BaseMapReq req,
                                     SimpleObserver<LackDiffResp> observer) {
        ReportService.INSTANCE
                .queryLackDiff(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询缺货商品明细表
     *
     * @param req
     * @param observer
     */
    public static void queryLackDetails(BaseMapReq req, SimpleObserver<LackDetailsResp> observer) {
        ReportService.INSTANCE
                .queryLackDetails(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询退货原因统计
     *
     * @param req
     * @param observer
     */
    public static void queryRefundReasonStatistic(BaseMapReq req, SimpleObserver<SingleListResp<RefundReasonBean>> observer) {
        ReportService.INSTANCE
                .queryRefundReasonStatics(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询毛利
     *
     * @param req
     * @param observer
     */
    public static void queryProfit(BaseMapReq req, SimpleObserver<ProfitResp> observer) {
        ReportService.INSTANCE
                .queryProfit(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询应收账款明细表
     *
     * @param daily    是否为日应收账款
     * @param observer
     */
    public static void queryCreditDetails(boolean daily, BaseMapReq req, SimpleObserver<CreditDetailsResp> observer) {
        Observable<BaseResp<CreditDetailsResp>> observable;
        if (daily) {
            observable = ReportService.INSTANCE.queryDailyCreditDetails(req);
        } else {
            observable = ReportService.INSTANCE.queryCustomerCreditDetails(req);
        }
        observable
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
