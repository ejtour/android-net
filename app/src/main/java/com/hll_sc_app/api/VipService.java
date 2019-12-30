package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 小流量接口请求
 *
 * @author zhuyingsong
 * @date 2018/5/14
 */
public interface VipService {
    VipService INSTANCE = HttpFactory.create(VipService.class, HttpConfig.getVipHost());

    /**
     * 判断小流量
     *
     * @param body 请求
     * @return observable
     */
    @POST("/shopmall/urlRouter")
    @Headers("pv:99999")
    Observable<BaseResp<String>> getVipService(@Body BaseReq<Object> body);
}

