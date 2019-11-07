package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.bill.BillListResp;
import com.hll_sc_app.bean.cardmanage.CardLogResp;
import com.hll_sc_app.bean.cardmanage.CardManageListResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CardManageService {
    CardManageService INSTANCE = HttpFactory.create(CardManageService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:101120")
    Observable<BaseResp<CardManageListResp>> queryCardManageList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101118")
    Observable<BaseResp<Object>> openCard(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:101122")
    Observable<BaseResp<Object>> recharge(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101119")
    Observable<BaseResp<Object>> changeCardStatus(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101121")
    Observable<BaseResp<CardLogResp>> queryCardLog(@Body BaseMapReq req);




}
