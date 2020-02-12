package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyEditReq;
import com.hll_sc_app.bean.daily.DailyReplyBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public interface DailyService {
    DailyService INSTANCE = HttpFactory.create(DailyService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:107017")
    Observable<BaseResp<SingleListResp<DailyBean>>> querySendDaily(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107016")
    Observable<BaseResp<DailyBean>> querySendDailyDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107019")
    Observable<BaseResp<SingleListResp<DailyBean>>> queryReceiveDaily(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107018")
    Observable<BaseResp<DailyBean>> queryReceiveDailyDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107020")
    Observable<BaseResp<Object>> replyDaily(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:107015")
    Observable<BaseResp<Object>> addDaily(@Body BaseReq<DailyEditReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:107022")
    Observable<BaseResp<Object>> editDaily(@Body BaseReq<DailyEditReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:107021")
    Observable<BaseResp<SingleListResp<DailyReplyBean>>> queryDailyReply(@Body BaseMapReq req);
}
