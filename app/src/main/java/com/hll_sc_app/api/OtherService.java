package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandEntryBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.LostResp;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.bean.other.RouteDetailResp;
import com.hll_sc_app.bean.rank.OrgRankBean;
import com.hll_sc_app.bean.rank.SalesRankResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public interface OtherService {
    OtherService INSTANCE = HttpFactory.create(OtherService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103119")
    Observable<BaseResp<SingleListResp<RouteBean>>> queryRouteList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103119")
    Observable<BaseResp<RouteDetailResp>> queryRouteDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103105")
    Observable<BaseResp<SalesRankResp>> querySalesRank(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103106")
    Observable<BaseResp<SingleListResp<OrgRankBean>>> queryShopRank(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111035")
    Observable<BaseResp<SingleListResp<OrgRankBean>>> queryGroupRank(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111050")
    Observable<BaseResp<LostResp>> queryLostInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111051")
    Observable<BaseResp<AnalysisResp>> queryAnalysisInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111052")
    Observable<BaseResp<TopTenResp>> queryTopTenInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100116")
    Observable<BaseResp<SingleListResp<GoodsDemandBean>>> queryGoodsDemand(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100114")
    Observable<BaseResp<Object>> addGoodsDemand(@Body BaseReq<GoodsDemandReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:100174")
    Observable<BaseResp<Object>> editGoodsDemand(@Body BaseReq<GoodsDemandReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:100115")
    Observable<BaseResp<Object>> replyGoodsDemand(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100158")
    Observable<BaseResp<SingleListResp<SpecialDemandEntryBean>>> querySpecialDemandCustomer(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100157")
    Observable<BaseResp<SingleListResp<SpecialDemandBean>>> querySpecialDemand(@Body BaseMapReq req);
}
