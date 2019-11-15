package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.customer.CrmCustomerResp;
import com.hll_sc_app.bean.customer.CrmShopResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/15
 */

public interface CustomerService {
    CustomerService INSTANCE = HttpFactory.create(CustomerService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:102050")
    Observable<BaseResp<CrmShopResp>> queryShopInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107009")
    Observable<BaseResp<CrmCustomerResp>> queryCustomerInfo(@Body BaseMapReq req);
}
