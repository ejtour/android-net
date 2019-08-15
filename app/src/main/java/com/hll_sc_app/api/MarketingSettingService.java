package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.marketingsetting.MarketingListResp;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddReq;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddResp;
import com.hll_sc_app.bean.marketingsetting.MarketingStatusBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 营销设置
 */

public interface MarketingSettingService {
    MarketingSettingService INSTANCE = HttpFactory.create(MarketingSettingService.class);

    /**
     * 获取促销状态
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:112007")
    Observable<BaseResp<List<MarketingStatusBean>>> getMarketingStatus(@Body BaseMapReq req);

    /**
     * 获取商品促销列表请求
     */
    @POST(HttpConfig.URL)
    @Headers("pv:112003")
    Observable<BaseResp<MarketingListResp>> getMarketingList(@Body BaseMapReq req);


    /**
     * 商品促销新增接口
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:112001")
    Observable<BaseResp<MarketingProductAddResp>> addProductMarketing(@Body BaseReq<MarketingProductAddReq> req);
}
