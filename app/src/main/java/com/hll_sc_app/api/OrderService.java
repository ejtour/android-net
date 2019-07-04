package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.export.OrderExportReq;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.bean.order.deliver.ModifyDeliverInfoReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.bean.order.search.OrderSearchResp;
import com.hll_sc_app.bean.order.settle.CashierResp;
import com.hll_sc_app.bean.order.settle.PayWaysResp;
import com.hll_sc_app.bean.order.settle.SettlementResp;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
import com.hll_sc_app.bean.order.transfer.OrderResultResp;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.bean.order.transfer.TransferResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public interface OrderService {
    OrderService INSTANCE = HttpFactory.create(OrderService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103001")
    Observable<BaseResp<List<OrderResp>>> getOrderList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103706")
    Observable<BaseResp<TransferResp>> getPendingTransferList(@Body BaseMapReq req);

    @Headers("pv:103002")
    @POST(HttpConfig.URL)
    Observable<BaseResp<OrderResp>> getOrderDetails(@Body BaseMapReq req);

    @Headers("pv:103004")
    @POST(HttpConfig.URL)
    Observable<BaseResp<Object>> modifyOrderStatus(@Body BaseMapReq req);

    @Headers("pv:103083")
    @POST(HttpConfig.URL)
    Observable<BaseResp<OrderSearchResp>> requestSearch(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103010")
    Observable<BaseResp<List<DeliverInfoResp>>> getOrderDeliverInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103011")
    Observable<BaseResp<DeliverNumResp>> getOrderDeliverNum(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103109")
    Observable<BaseResp<List<DeliverShopResp>>> getOrderDeliverShop(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103005")
    Observable<BaseResp<Object>> modifyDeliverInfo(@Body BaseReq<ModifyDeliverInfoReq> req);

    @POST(HttpConfig.URL)
    @Headers("pv:103017")
    Observable<BaseResp<ExportResp>> exportDelivery(@Body BaseReq<OrderExportReq> req);

    @POST(HttpConfig.URL)
    @Headers("pv:103070")
    Observable<BaseResp<ExportResp>> exportAssembly(@Body BaseReq<OrderExportReq> req);

    @POST(HttpConfig.URL)
    @Headers("pv:103074")
    Observable<BaseResp<ExportResp>> exportNormal(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103124")
    Observable<BaseResp<ExportResp>> exportSpecial(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101107")
    Observable<BaseResp<ExpressResp>> getExpressCompanyList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103021")
    Observable<BaseResp<OrderInspectionResp>> inspectionOrder(@Body BaseReq<OrderInspectionReq> req);

    @POST(HttpConfig.URL)
    @Headers("pv:101089")
    Observable<BaseResp<PayWaysResp>> getPayWays(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103049")
    Observable<BaseResp<Object>> inspectionPay(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103044")
    Observable<BaseResp<CashierResp>> getCashier(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103045")
    Observable<BaseResp<SettlementResp>> getSettlementStatus(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103714")
    Observable<BaseResp<OrderResultResp>> mallOrder(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103719")
    Observable<BaseResp<OrderResultResp>> batchMallOrder(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103705")
    Observable<BaseResp<TransferBean>> getTransferDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103717")
    Observable<BaseResp<Object>> cancelTransfer(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103726")
    Observable<BaseResp<Object>> commitInventoryCheck(@Body BaseReq<InventoryCheckReq> req);
}
