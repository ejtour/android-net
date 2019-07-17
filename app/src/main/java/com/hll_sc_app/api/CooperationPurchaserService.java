package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 合作采购商
 *
 * @author zhuyingsong
 * @date 2019-07-17
 */
public interface CooperationPurchaserService {
    CooperationPurchaserService INSTANCE = HttpFactory.create(CooperationPurchaserService.class);

    /**
     * 查询合作餐企列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102007")
    Observable<BaseResp<CooperationPurchaserResp>> queryCooperationPurchaserList(@Body BaseMapReq req);

    /**
     * 删除合作餐企
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102022")
    Observable<BaseResp<Object>> delCooperationPurchaser(@Body BaseMapReq req);
}
