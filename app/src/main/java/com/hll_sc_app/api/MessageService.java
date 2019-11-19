package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.bean.message.MessageDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public interface MessageService {
    MessageService INSTANCE = HttpFactory.create(MessageService.class, HttpConfig.getMessageHost());

    @POST(HttpConfig.URL)
    @Headers("pv:108002")
    Observable<BaseResp<List<MessageBean>>> queryMessageSummary(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:109002")
    Observable<BaseResp<SingleListResp<MessageBean>>> queryMessageList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108003")
    Observable<BaseResp<List<MessageDetailBean>>> queryMessageDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108004")
    Observable<BaseResp<Object>> markAsRead(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:109007")
    Observable<BaseResp<Object>> clearUnreadMessage(@Body BaseMapReq req);
}
