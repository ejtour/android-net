package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.DomesticPriceBean;
import com.hll_sc_app.bean.price.LocalPriceBean;
import com.hll_sc_app.bean.price.MarketBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public interface PriceService {
    PriceService INSTANCE = HttpFactory.create(PriceService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:100094")
    Observable<BaseResp<List<MarketBean>>> queryMarketList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100095")
    Observable<BaseResp<List<CategoryBean>>> queryLocalCategory(@Body BaseReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:100233")
    Observable<BaseResp<SingleListResp<LocalPriceBean>>> queryLocalPrice(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100097")
    Observable<BaseResp<SingleListResp<DomesticPriceBean>>> queryPriceAvg(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100098")
    Observable<BaseResp<SingleListResp<DomesticPriceBean>>> queryPriceGain(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:100099")
    Observable<BaseResp<List<CategoryBean>>> queryDomesticCategory(@Body BaseReq body);
}
