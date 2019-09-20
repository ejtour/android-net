package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.ComplainHistoryResp;
import com.hll_sc_app.bean.complain.ComplainInnerLogResp;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.bean.complain.ComplainStatusResp;
import com.hll_sc_app.bean.complain.DepartmentsBean;

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

    @POST(HttpConfig.URL)
    @Headers("pv:110021")
    Observable<BaseResp<ComplainStatusResp>> queryComplainStatus(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110020")
    Observable<BaseResp<ComplainDetailResp>> queryComplainDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110024")
    Observable<BaseResp<ComplainHistoryResp>> queryComplainHistory(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110018")
    Observable<BaseResp<ComplainInnerLogResp>> queryComplainInnerLog(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110022")
    Observable<BaseResp<List<DepartmentsBean>>> queryDepartments(@Body BaseMapReq req);
}
