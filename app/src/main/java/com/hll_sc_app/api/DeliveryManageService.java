package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.delivery.DeliveryBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 配送设置
 *
 * @author zhuyingsong
 * @date 2019-07-12
 */
public interface DeliveryManageService {
    DeliveryManageService INSTANCE = HttpFactory.create(DeliveryManageService.class);

    /**
     * 查询配送方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101106")
    Observable<BaseResp<DeliveryBean>> queryDeliveryList(@Body BaseMapReq req);

    /**
     * 修改配送方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101104")
    Observable<BaseResp<Object>> editDeliveryType(@Body BaseMapReq req);
}
