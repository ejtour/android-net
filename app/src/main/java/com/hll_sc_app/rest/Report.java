package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackResp;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeReq;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.inspectLack.InspectLackResp;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailReq;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailResp;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossReq;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.bean.report.produce.ProduceInputReq;
import com.hll_sc_app.bean.report.produce.ProduceSummaryResp;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.bean.report.purchase.ManHourReq;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;
import com.hll_sc_app.bean.report.refund.RefundedCustomerReq;
import com.hll_sc_app.bean.report.refund.RefundedCustomerResp;
import com.hll_sc_app.bean.report.refund.RefundedProductReq;
import com.hll_sc_app.bean.report.refund.RefundedProductResp;
import com.hll_sc_app.bean.report.refund.RefundedReq;
import com.hll_sc_app.bean.report.refund.RefundedResp;
import com.hll_sc_app.bean.report.refund.WaitRefundCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundProductResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.report.req.ReportExportReq;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.bean.report.search.SearchReq;
import com.hll_sc_app.bean.report.search.SearchResultResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseShipperReq;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Date;
import java.util.List;

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
     * @param requestParams 查询参数
     */
    public static void queryCustomerLack(CustomerLackReq requestParams, SimpleObserver<CustomerLackResp> observer) {
        ReportService.INSTANCE
                .queryCustomerLack(new BaseReq<>(requestParams))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 收货差异汇总
     *
     * @param params
     * @param observer
     */
    public static void queryInspectLack(BaseReportReqParam params, SimpleObserver<InspectLackResp> observer) {
        ReportService.INSTANCE
                .queryInspectLack(new BaseReq<>(params))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 收货差异明细
     *
     * @param params
     * @param observer
     */
    public static void queryInspectLackDetail(InspectLackDetailReq params, SimpleObserver<InspectLackDetailResp> observer) {
        ReportService.INSTANCE
                .queryInspectLackDetail(new BaseReq<>(params))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 收货差异明细
     *
     * @param params
     * @param observer
     */
    public static void queryWareHouseProductLackDetail(WareHouseLackProductReq params, SimpleObserver<WareHouseLackProductResp> observer) {
        ReportService.INSTANCE
                .queryWareHouseProductLackDetail(new BaseReq<>(params))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 代仓货主列表
     *
     * @param groupID
     * @param actionType
     * @param searchWord
     * @param observer
     */
    public static void queryWareHouseShipperList(String groupID, int actionType, String searchWord, SimpleObserver<List<WareHouseShipperBean>> observer) {
        WareHouseShipperReq params = new WareHouseShipperReq();
        params.setGroupID(groupID);
        params.setActionType(actionType);
        params.setName(searchWord);
        ReportService.INSTANCE
                .queryWareHouseShipperList(new BaseReq<>(params))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 配送及时率查询
     *
     * @param deliveryTimeReq
     * @param observer
     */
    public static void queryDeliveryTimeContent(DeliveryTimeReq deliveryTimeReq, SimpleObserver<DeliveryTimeResp> observer) {
        ReportService.INSTANCE.queryDeliveryTimeContent(new BaseReq<>(deliveryTimeReq))
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
     * @param flag
     * @param observer
     */
    public static void queryRefundTotal(int flag, SimpleObserver<WaitRefundTotalResp> observer) {
        ReportService.INSTANCE.queryRefundTotal(BaseMapReq.newBuilder().put("flag", flag + "").put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 待退货退款 集团列表查询
     *
     * @param waitRefundReq
     * @param observer
     */
    public static void queryWaitRefundCustomerList(WaitRefundReq waitRefundReq, SimpleObserver<WaitRefundCustomerResp> observer) {
        ReportService.INSTANCE.queryRefundCustomerList(new BaseReq<>(waitRefundReq))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 待退货 商品列表查询
     *
     * @param waitRefundReq
     * @param observer
     */
    public static void queryWaitRefundProductList(WaitRefundReq waitRefundReq, SimpleObserver<WaitRefundProductResp> observer) {
        ReportService.INSTANCE.queryRefundProductList(new BaseReq<>(waitRefundReq))
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
    public static void queryRefundedDetail(RefundedReq req, SimpleObserver<RefundedResp> observer) {
        ReportService.INSTANCE.queryRefundedDetail(new BaseReq<>(req))
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
    public static void queryRefundedProductList(RefundedProductReq req, SimpleObserver<RefundedProductResp> observer) {
        ReportService.INSTANCE.queryRefundProductDetail(new BaseReq<>(req))
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
    public static void queryRefundedCustomerList(RefundedCustomerReq req, SimpleObserver<RefundedCustomerResp> observer) {
        ReportService.INSTANCE.queryRefundedCustomerDetail(new BaseReq<>(req))
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
     * @param req
     * @param observer
     */
    public static void queryCustomerOrShopLossDetail(CustomerAndShopLossReq req, SimpleObserver<CustomerAndShopLossResp> observer){
        ReportService.INSTANCE.queryCustomerOrShopLossDetail(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 代仓发货统计
     * @param req
     * @param observer
     */
    public static void queryWareHouseDeliveryInfo(WareHouseDeliveryReq req, SimpleObserver<WareHouseDeliveryResp> observer){
        ReportService.INSTANCE.queryWareHouseDeliveryInfo(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 代仓服务费统计
     * @param req
     * @param observer
     */
    public static void queryWareHouseServiceFee(WareHouseServiceFeeReq req, SimpleObserver<WareHouseServiceFeeResp> observer){
        ReportService.INSTANCE.queryWareHouseServiceFee(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 搜索查询
     * @param req
     * @param observer
     */
    public static void querySearchList(SearchReq req, SimpleObserver<SearchResultResp> observer){
        ReportService.INSTANCE.querySearchList(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
