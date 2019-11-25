package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.CrmCustomerResp;
import com.hll_sc_app.bean.customer.CrmShopResp;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.bean.customer.VisitRecordBean;

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

    @POST(HttpConfig.URL)
    @Headers("pv:107001")
    Observable<BaseResp<Object>> saveIntentCustomer(@Body BaseReq<CustomerBean> body);

    @POST(HttpConfig.URL)
    @Headers("pv:107002")
    Observable<BaseResp<SingleListResp<CustomerBean>>> queryIntentCustomer(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107007")
    Observable<BaseResp<SingleListResp<VisitRecordBean>>> queryVisitRecord(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107008")
    Observable<BaseResp<Object>> delVisitRecord(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107004")
    Observable<BaseResp<Object>> saveVisitRecord(@Body BaseReq<VisitRecordBean> body);

    @POST(HttpConfig.URL)
    @Headers("pv:107005")
    Observable<BaseResp<SingleListResp<VisitPlanBean>>> queryVisitPlan(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107006")
    Observable<BaseResp<Object>> delVisitPlan(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107003")
    Observable<BaseResp<Object>> saveVisitPlan(@Body BaseReq<VisitPlanBean> body);
}
