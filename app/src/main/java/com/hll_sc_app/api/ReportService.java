package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.credit.CreditBean;
import com.hll_sc_app.bean.report.credit.CreditDetailsResp;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleDetailResp;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleResp;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.bean.report.daily.SalesDailyBean;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.lack.CustomerLackResp;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.bean.report.lack.LackDiffResp;
import com.hll_sc_app.bean.report.loss.LossBean;
import com.hll_sc_app.bean.report.marketing.MarketingDetailResp;
import com.hll_sc_app.bean.report.marketing.MarketingResp;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailBean;
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
    Observable<BaseResp<SingleListResp<OrderGoodsBean>>> queryOrderGoods(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103100")
    Observable<BaseResp<ExportResp>> exportOrderGoodsDetails(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:111037")
    Observable<BaseResp<ExportResp>> exportReport(@Body BaseReq<ReportExportReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:103099")
    Observable<BaseResp<SingleListResp<OrderGoodsDetailBean>>> queryOrderGoodsDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103096")
    Observable<BaseResp<ProductSaleResp>> queryProductSales(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103097")
    Observable<BaseResp<ProductSaleTop10Resp>> queryProductSalesTop10(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111077")
    Observable<BaseResp<SingleListResp<RefundReasonBean>>> queryRefundReasonStatics(@Body BaseMapReq req);

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
    @Headers("pv:111012")
    Observable<BaseResp<LackDetailsResp>> queryLackDetails(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111053")
    Observable<BaseResp<CustomerLackResp>> queryCustomerLack(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111007")
    Observable<BaseResp<ReceiveDiffResp>> queryReceiveDiff(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111013")
    Observable<BaseResp<ReceiveDiffDetailsResp>> queryReceiveDiffDetails(@Body BaseMapReq req);

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
    Observable<BaseResp<RefundResp>> queryRefundInfo(@Body BaseMapReq body);

    /**
     * 待退货退款 集团列表查询
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111014")
    Observable<BaseResp<RefundCustomerResp>> queryRefundCustomerList(@Body BaseMapReq body);


    /**
     * 待退货 商品列表查询
     *
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111015")
    Observable<BaseResp<RefundProductResp>> queryRefundProductList(@Body BaseMapReq req);

    /**
     * 退货明细
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111021")
    Observable<BaseResp<RefundDetailsResp>> queryRefundDetail(@Body BaseMapReq req);

    /**
     * 已退商品客户列表统计
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111020")
    Observable<BaseResp<RefundCustomerResp>> queryRefundCustomerDetail(@Body BaseMapReq body);


    /**
     * 退货商品统计
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111022")
    Observable<BaseResp<RefundProductResp>> queryRefundProductDetail(@Body BaseMapReq body);

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
    Observable<BaseResp<SingleListResp<LossBean>>> queryLossInfo(@Body BaseMapReq body);

    /**
     * 代仓发货统计
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111067")
    Observable<BaseResp<SingleListResp<WareHouseDeliveryBean>>> queryWareHouseDelivery(@Body BaseMapReq req);

    /**
     * 代仓服务费统计
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111093")
    Observable<BaseResp<SingleListResp<WareHouseFeeBean>>> queryWareHouseFee(@Body BaseMapReq req);

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
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111045")
    Observable<BaseResp<SingleListResp<SalesDailyBean>>> querySalesDaily(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:103170")
    Observable<BaseResp<CustomReceiveListResp>> queryCustomReceiveList(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:103171")
    Observable<BaseResp<List<CustomReceiveDetailBean>>> queryCustomReceiveDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111026")
    Observable<BaseResp<ProfitResp>> queryProfit(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111016")
    Observable<BaseResp<SingleListResp<CreditBean>>> queryCustomerCredit(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111017")
    Observable<BaseResp<CreditDetailsResp>> queryCustomerCreditDetails(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111018")
    Observable<BaseResp<CreditDetailsResp>> queryDailyCreditDetails(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103180")
    Observable<BaseResp<SingleListResp<ReceiveCustomerBean>>> queryReceiptList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103191")
    Observable<BaseResp<CustomerSettleResp>> queryCustomerSettle(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103192")
    Observable<BaseResp<CustomerSettleDetailResp>> queryCustomerSettleDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111134")
    Observable<BaseResp<MarketingResp>> queryMarketing(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111135")
    Observable<BaseResp<MarketingDetailResp>> queryMarketingDetail(@Body BaseMapReq req);
}
