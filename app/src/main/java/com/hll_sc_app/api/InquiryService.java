package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

public interface InquiryService {
    InquiryService INSTANCE = HttpFactory.create(InquiryService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:100199")
    Observable<BaseResp<SingleListResp<InquiryBean>>> queryList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100220")
    Observable<BaseResp<InquiryBean>> queryDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100221")
    Observable<BaseResp<InquiryBindResp>> bindResult(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100222")
    Observable<BaseResp<Object>> submit(@Body BaseReq<InquiryBean> body);
}
