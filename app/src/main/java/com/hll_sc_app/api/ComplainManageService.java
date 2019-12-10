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
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;
import com.hll_sc_app.bean.complain.FeedbackListResp;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;

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
    Observable<BaseResp<List<DropMenuBean>>> queryDropMenus(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:110019")
    Observable<BaseResp<Object>> saveComplainInnerLog(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110015")
    Observable<BaseResp<Object>> sendComplainReply(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110016")
    Observable<BaseResp<Object>> applyPlatformInject(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:111027")
    Observable<BaseResp<ReportFormSearchResp>> queryReportFormPurchaserList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:110017")
    Observable<BaseResp<Object>> changeComplainStatus(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101074")
    Observable<BaseResp<FeedbackListResp>> queryFeedbackList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101071")
    Observable<BaseResp<Object>> addFeedback(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101073")
    Observable<BaseResp<FeedbackDetailResp>> queryFeedbackDetail(@Body BaseMapReq req);









}
