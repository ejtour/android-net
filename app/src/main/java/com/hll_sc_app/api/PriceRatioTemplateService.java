package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 价格比例设置
 *
 * @author zhuyingsong
 * @date 2019-07-22
 */
public interface PriceRatioTemplateService {
    PriceRatioTemplateService INSTANCE = HttpFactory.create(PriceRatioTemplateService.class);

    /**
     * 查询比例模板详情
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100069")
    Observable<BaseResp<List<CopyCategoryBean>>> queryRatioTemplateDetail(@Body BaseMapReq req);

    /**
     * 查询比例模板列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100068")
    Observable<BaseResp<RatioTemplateResp>> queryRatioTemplateList(@Body BaseMapReq req);

    /**
     * 删除报价模板
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100067")
    Observable<BaseResp<Object>> delPriceRatioTemplate(@Body BaseMapReq req);


}
