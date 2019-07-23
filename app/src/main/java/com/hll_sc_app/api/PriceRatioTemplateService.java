package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.agreementprice.quotation.CategoryRatioListBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
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
    Observable<BaseResp<List<CategoryRatioListBean>>> queryRatioTemplateDetail(@Body BaseMapReq req);

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

    /**
     * 编辑报价模板
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100066")
    Observable<BaseResp<Object>> editPriceRatioTemplate(@Body BaseReq<RatioTemplateBean> req);

    /**
     * 编辑报价模板
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100065")
    Observable<BaseResp<Object>> addPriceRatioTemplate(@Body BaseReq<RatioTemplateBean> req);


}
