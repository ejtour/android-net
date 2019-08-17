package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.bean.invoice.InvoiceHistoryResp;
import com.hll_sc_app.bean.invoice.InvoiceListResp;
import com.hll_sc_app.bean.invoice.InvoiceMakeReq;
import com.hll_sc_app.bean.invoice.InvoiceMakeResp;
import com.hll_sc_app.bean.invoice.InvoiceOrderResp;
import com.hll_sc_app.bean.invoice.ReturnRecordResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public interface InvoiceService {
    InvoiceService INSTANCE = HttpFactory.create(InvoiceService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103134")
    Observable<BaseResp<InvoiceListResp>> getInvoiceList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103147")
    Observable<BaseResp<InvoiceOrderResp>> getRelevanceOrderList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103136")
    Observable<BaseResp<InvoiceHistoryResp>> getInvoiceHistory(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103133")
    Observable<BaseResp<InvoiceMakeResp>> makeInvoice(@Body BaseReq<InvoiceMakeReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:103138")
    Observable<BaseResp<InvoiceBean>> getInvoiceDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103135")
    Observable<BaseResp<Object>> doAction(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103150")
    Observable<BaseResp<Object>> settle(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103148")
    Observable<BaseResp<ReturnRecordResp>> updateReturnRecord(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103153")
    Observable<BaseResp<Object>> modifyInvoiceInfo(@Body BaseMapReq req);
}
