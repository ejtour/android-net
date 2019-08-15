package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.RefundReasonStaticsResp;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackResp;
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGatherResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
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

}
