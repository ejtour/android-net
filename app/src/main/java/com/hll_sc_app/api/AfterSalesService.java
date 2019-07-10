package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.aftersales.AfterSalesActionReq;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;
import com.hll_sc_app.bean.aftersales.PurchaserListResp;
import com.hll_sc_app.bean.export.ExportResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public interface AfterSalesService {
    AfterSalesService INSTANCE = HttpFactory.create(AfterSalesService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103052")
    Observable<BaseResp<List<AfterSalesBean>>> getAfterSalesList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103055")
    Observable<BaseResp<PurchaserListResp>> getPurchaserShopList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103129")
    Observable<BaseResp<Object>> modifyUnitPrice(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103107")
    Observable<BaseResp<ExportResp>> exportAfterSalesOrder(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103058")
    Observable<BaseResp<Object>> afterSalesAction(@Body BaseReq<AfterSalesActionReq> req);

    @POST(HttpConfig.URL)
    @Headers("pv:103053")
    Observable<BaseResp<NegotiationHistoryResp>> getNegotiationHistory(@Body BaseMapReq req);
}
