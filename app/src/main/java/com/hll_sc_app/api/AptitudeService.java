package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeExpireResp;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public interface AptitudeService {
    AptitudeService INSTANCE = HttpFactory.create(AptitudeService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:101186")
    Observable<BaseResp<Object>> saveBaseInfo(@Body BaseReq<AptitudeInfoReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101187")
    Observable<BaseResp<AptitudeInfoResp>> queryBaseInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101188")
    Observable<BaseResp<Object>> saveAptitudeList(@Body BaseReq<AptitudeReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101183")
    Observable<BaseResp<SingleListResp<AptitudeBean>>> queryAptitudeList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101212")
    Observable<BaseResp<List<AptitudeBean>>> queryAptitudeTypeList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100248")
    Observable<BaseResp<List<GoodsBean>>> queryGoodsList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101208")
    Observable<BaseResp<Object>> addAptitudeType(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101209")
    Observable<BaseResp<Object>> delAptitudeType(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101214")
    Observable<BaseResp<SingleListResp<AptitudeBean>>> queryGoodsAptitudeList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101216")
    Observable<BaseResp<AptitudeBean>> queryGoodsAptitude(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101215")
    Observable<BaseResp<Object>> delGoodsAptitude(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101213")
    Observable<BaseResp<Object>> addGoodsAptitude(@Body BaseReq<AptitudeBean> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101218")
    Observable<BaseResp<Object>> editGoodsAptitude(@Body BaseReq<AptitudeBean> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101221")
    Observable<BaseResp<AptitudeExpireResp>> expireReminderEnterprise(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101222")
    Observable<BaseResp<AptitudeExpireResp>> expireReminderGoods(@Body BaseMapReq req);
}
