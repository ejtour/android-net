package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.SpecsStatusReq;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 商品管理
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public interface GoodsService {
    GoodsService INSTANCE = HttpFactory.create(GoodsService.class);

    /**
     * 获取商品详情
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100005")
    Observable<BaseResp<GoodsBean>> queryGoodsDetail(@Body BaseMapReq req);

    /**
     * 复制商城分类为自定义分类
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100059")
    Observable<BaseResp<CustomCategoryBean>> copyToCustomCategory(@Body BaseMapReq req);

    /**
     * 商品搜索
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100006")
    Observable<BaseResp<List<GoodsBean>>> queryGoodsList(@Body BaseReq<GoodsListReq> req);

    /**
     * 商品规格状态修改（上下架）
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100083")
    Observable<BaseResp<Object>> updateSpecStatus(@Body BaseReq<SpecsStatusReq> req);
}
