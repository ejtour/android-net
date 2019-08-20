package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SalesVolumeResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public interface HomeService {
    HomeService INSTANCE = HttpFactory.create(HomeService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:111063")
    Observable<BaseResp<SalesVolumeResp>> querySalesVolume(@Body BaseMapReq req);
}
