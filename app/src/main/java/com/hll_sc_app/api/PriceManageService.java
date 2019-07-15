package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 售价设置
 *
 * @author zhuyingsong
 * @date 2019-07-12
 */
public interface PriceManageService {
    PriceManageService INSTANCE = HttpFactory.create(PriceManageService.class);

    /**
     * 商品价格修改服务
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100017")
    Observable<BaseResp<Object>> updateProductPrice(@Body BaseMapReq req);

    /**
     * 商品成本价修改
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100071")
    Observable<BaseResp<Object>> updateCostPrice(@Body BaseMapReq req);
}
