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
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGatherResp;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeReq;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.inspectLack.InspectLackResp;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailReq;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.bean.report.refund.RefundedCustomerReq;
import com.hll_sc_app.bean.report.refund.RefundedCustomerResp;
import com.hll_sc_app.bean.report.refund.RefundedProductReq;
import com.hll_sc_app.bean.report.refund.RefundedProductResp;
import com.hll_sc_app.bean.report.refund.RefundedReq;
import com.hll_sc_app.bean.report.refund.RefundedResp;
import com.hll_sc_app.bean.report.refund.WaitRefundCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundProductResp;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.req.ProductDetailReq;
import com.hll_sc_app.bean.report.req.ReportExportReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.bean.report.resp.product.OrderDetailTotalResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductResp;
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
     * 商品统计（明细）
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111040")
    Observable<BaseResp<OrderDetailTotalResp>> queryProductDetailList(@Body BaseReq<ProductDetailReq> req);

    /**
     * 日销售额查询
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111005")
    Observable<BaseResp<DateSaleAmountResp>> queryDateSaleAmount(@Body BaseReq<BaseReportReqParam> req);

    /**
     * 客户销售/门店汇总查询
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111004")
    Observable<BaseResp<CustomerSalesResp>> queryCustomerSales(@Body BaseReq<CustomerSaleReq> req);

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
    @Headers("pv:101024")
    Observable<BaseResp<List<PurchaserGroupBean>>> queryPurchaser(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:111009")
    Observable<BaseResp<SalesManSignResp>> querySalesManSignAchievement(@Body BaseReq<SalesManAchievementReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111003")
    Observable<BaseResp<SalesManSalesResp>> querySalesManSalesAchievement(@Body BaseReq<SalesManAchievementReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111006")
    Observable<BaseResp<DeliveryLackGatherResp>> queryDeliveryLackGather(@Body BaseReq<BaseReportReqParam> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111053")
    Observable<BaseResp<CustomerLackResp>> queryCustomerLack(@Body BaseReq<CustomerLackReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111007")
    Observable<BaseResp<InspectLackResp>> queryInspectLack(@Body BaseReq<BaseReportReqParam> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111013")
    Observable<BaseResp<InspectLackDetailResp>> queryInspectLackDetail(@Body BaseReq<InspectLackDetailReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111012")
    Observable<BaseResp<WareHouseLackProductResp>> queryWareHouseProductLackDetail(@Body BaseReq<WareHouseLackProductReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101049")
    Observable<BaseResp<List<WareHouseShipperBean>>> queryWareHouseShipperList(@Body BaseReq<WareHouseShipperReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:111008")
    Observable<BaseResp<DeliveryTimeResp>> queryDeliveryTimeContent(@Body BaseReq<DeliveryTimeReq> body);

    /**
     * 退款合计
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111010")
    Observable<BaseResp<WaitRefundTotalResp>> queryRefundTotal(@Body BaseMapReq body);

    /**
     * 待退货退款 集团列表查询
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111014")
    Observable<BaseResp<WaitRefundCustomerResp>> queryRefundCustomerList(@Body BaseReq<WaitRefundReq> body);


    /**
     * 待退货 商品列表查询
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111015")
    Observable<BaseResp<WaitRefundProductResp>> queryRefundProductList(@Body BaseReq<WaitRefundReq> body);

    /**
     * 退货明细
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111021")
    Observable<BaseResp<RefundedResp>> queryRefundedDetail(@Body BaseReq<RefundedReq> body);

    /**
     * 已退商品客户列表统计
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111020")
    Observable<BaseResp<RefundedCustomerResp>> queryRefundedCustomerDetail(@Body BaseReq<RefundedCustomerReq> body);


    /**
     * 退货商品统计
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:111022")
    Observable<BaseResp<RefundedProductResp>> queryRefundProductDetail(@Body BaseReq<RefundedProductReq> body);
}
