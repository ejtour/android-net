package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.invoice.InvoiceListResp;

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
}
