package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.RefundReasonStaticsResp;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackResp;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.bean.report.lack.LackDiffResp;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.receive.ReceiveDiffResp;
import com.hll_sc_app.bean.report.receive.ReceiveDiffDetailsResp;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossReq;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossResp;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsResp;
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
import com.hll_sc_app.bean.report.req.ReportExportReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.bean.report.salesReport.SalesReportReq;
import com.hll_sc_app.bean.report.salesReport.SalesReportResp;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.bean.report.search.SearchReq;
import com.hll_sc_app.bean.report.search.SearchResultResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseShipperReq;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author chukun
 * @description 报表中心服务
 */
public interface ReportService {

    ReportService INSTANCE = HttpFactory.create(ReportService.class);

    /**
     * 日销售额查询
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111005")
    Observable<BaseResp<DateSaleAmountResp>> queryDateSaleAmount(@Body BaseMapReq req);

    /**
     * 客户销售/门店汇总查询
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111004")
    Observable<BaseResp<CustomerSalesResp>> queryCustomerSales(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103098")
    Observable<BaseResp<OrderGoodsResp<OrderGoodsBean>>> queryOrderGoods(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103100")
    Observable<BaseResp<ExportResp>> exportOrderGoodsDetails(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:111037")
    Observable<BaseResp<ExportResp>> exportReport(@Body BaseReq<ReportExportReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:103099")
    Observable<BaseResp<OrderGoodsResp<OrderGoodsDetailBean>>> queryOrderGoodsDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103096")
    Observable<BaseResp<ProductSaleResp>> queryProductSales(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103097")
    Observable<BaseResp<ProductSaleTop10Resp>> queryProductSalesTop10(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111077")
    Observable<BaseResp<RefundReasonStaticsResp>> queryRefundReasonStatics(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111009")
    Observable<BaseResp<SalesManSignResp>> querySalesManSignAchievement(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111003")
    Observable<BaseResp<SalesManSalesResp>> querySalesManSalesAchievement(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111006")
    Observable<BaseResp<LackDiffResp>> queryLackDiff(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111053")
    Observable<BaseResp<CustomerLackResp>> queryCustomerLack(@Body BaseReq<CustomerLackReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111007")
    Observable<BaseResp<ReceiveDiffResp>> queryReceiveDiff(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111013")
    Observable<BaseResp<ReceiveDiffDetailsResp>> queryReceiveDiffDetails(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111012")
    Observable<BaseResp<WareHouseLackProductResp>> queryWareHouseProductLackDetail(@Body BaseReq<WareHouseLackProductReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101049")
    Observable<BaseResp<List<WareHouseShipperBean>>> queryWareHouseShipperList(@Body BaseReq<WareHouseShipperReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111008")
    Observable<BaseResp<DeliveryTimeResp>> queryDeliveryTime(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111038")
    Observable<BaseResp<PurchaseSummaryResp>> queryPurchaseSummary(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111028")
    Observable<BaseResp<Object>> recordPurchaseInfo(@Body BaseMapReq req);

    /**
     * 退款合计
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111010")
    Observable<BaseResp<WaitRefundTotalResp>> queryRefundTotal(@Body BaseMapReq body);

    /**
     * 待退货退款 集团列表查询
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111014")
    Observable<BaseResp<WaitRefundCustomerResp>> queryRefundCustomerList(@Body BaseReq<WaitRefundReq> body);


    /**
     * 待退货 商品列表查询
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111015")
    Observable<BaseResp<WaitRefundProductResp>> queryRefundProductList(@Body BaseReq<WaitRefundReq> body);

    /**
     * 退货明细
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111021")
    Observable<BaseResp<RefundedResp>> queryRefundedDetail(@Body BaseReq<RefundedReq> body);

    /**
     * 已退商品客户列表统计
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111020")
    Observable<BaseResp<RefundedCustomerResp>> queryRefundedCustomerDetail(@Body BaseReq<RefundedCustomerReq> body);


    /**
     * 退货商品统计
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111022")
    Observable<BaseResp<RefundedProductResp>> queryRefundProductDetail(@Body BaseReq<RefundedProductReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111031")
    Observable<BaseResp<ProduceSummaryResp>> queryProduceSummary(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111073")
    Observable<BaseResp<List<ProduceDetailBean>>> queryProduceDetails(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111076")
    Observable<BaseResp<Object>> recordProduceInfo(@Body BaseReq<ProduceInputReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111034")
    Observable<BaseResp<List<ManHourBean>>> queryManHour(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111065")
    Observable<BaseResp<Object>> saveManHour(@Body BaseReq<ManHourReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111094")
    Observable<BaseResp<Object>> recordPeopleEffect(@Body BaseMapReq req);

    /**
     * 客户流失率统计（门店明细）
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111032")
    Observable<BaseResp<CustomerAndShopLossResp>> queryCustomerOrShopLossDetail(@Body BaseReq<CustomerAndShopLossReq> body);

    /**
     * 代仓发货统计
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111067")
    Observable<BaseResp<WareHouseDeliveryResp>> queryWareHouseDeliveryInfo(@Body BaseReq<WareHouseDeliveryReq> body);

    /**
     * 代仓服务费统计
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111093")
    Observable<BaseResp<WareHouseServiceFeeResp>> queryWareHouseServiceFee(@Body BaseReq<WareHouseServiceFeeReq> body);

    /**
     * 搜索
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103083")
    Observable<BaseResp<SearchResultResp>> querySearchList(@Body BaseReq<SearchReq> body);

    /**
     * 销售日报
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111045")
    Observable<BaseResp<SalesReportResp>> querySalesReportList(@Body BaseReq<SalesReportReq> body);


    @POST(HttpConfig.URL)
    @Headers("pv:103170")
    Observable<BaseResp<CustomReceiveListResp>> queryCustomReceiveList(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:103171")
    Observable<BaseResp<List<CustomReceiveDetailBean>>> queryCustomReceiveDetail(@Body BaseMapReq req);


}
