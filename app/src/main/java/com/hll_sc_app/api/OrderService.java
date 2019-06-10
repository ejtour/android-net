package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.search.OrderSearchResp;

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

    @Headers("pv:103002")
    @POST(HttpConfig.URL)
    Observable<BaseResp<OrderResp>> getOrderDetails(@Body BaseMapReq req);

    @Headers("pv:103004")
    @POST(HttpConfig.URL)
    Observable<BaseResp<Object>> modifyOrderStatus(@Body BaseMapReq req);

    @Headers("pv:103083")
    @POST(HttpConfig.URL)
    Observable<BaseResp<OrderSearchResp>> requestSearch(@Body BaseMapReq req);
}
