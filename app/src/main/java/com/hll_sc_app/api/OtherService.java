package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.bean.other.RouteDetailResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public interface OtherService {
    OtherService INSTANCE = HttpFactory.create(OtherService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103119")
    Observable<BaseResp<SingleListResp<RouteBean>>> queryRouteList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103119")
    Observable<BaseResp<RouteDetailResp>> queryRouteDetail(@Body BaseMapReq req);
}
