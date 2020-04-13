package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.message.MessageBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/7
 */

public interface IMService {
    IMService INSTANCE = HttpFactory.create(IMService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:109002")
    Observable<BaseResp<SingleListResp<MessageBean>>> queryMessageList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:109007")
    Observable<BaseResp<Object>> clearUnreadMessage(@Body BaseMapReq req);
}
