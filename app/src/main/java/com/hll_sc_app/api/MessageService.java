package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.message.ApplyMessageResp;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.bean.message.MessageDetailBean;
import com.hll_sc_app.bean.message.MessageSettingBean;
import com.hll_sc_app.bean.message.UnreadResp;

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
    @Headers("pv:108003")
    Observable<BaseResp<List<MessageDetailBean>>> queryMessageDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108004")
    Observable<BaseResp<Object>> markAsRead(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108001")
    Observable<BaseResp<UnreadResp>> queryUnreadNum(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108011")
    Observable<BaseResp<ApplyMessageResp>> queryApplyMessage(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108012")
    Observable<BaseResp<UnreadResp>> queryDemandMessage(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108013")
    Observable<BaseResp<Object>> markAllAsRead(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108021")
    Observable<BaseResp<List<MessageSettingBean>>> queryMessageSettings(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:108020")
    Observable<BaseResp<Object>> saveMessageSettings(@Body BaseMapReq req);
}
