package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.complain.ComplainListResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ComplainManageService {
    ComplainManageService INSTANCE = HttpFactory.create(ComplainManageService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:110013")
    Observable<BaseResp<ComplainListResp>> queryComplainList(@Body BaseMapReq req);


}
