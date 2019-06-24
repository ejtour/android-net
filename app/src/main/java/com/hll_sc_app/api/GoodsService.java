package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.CustomCategorySortReq;
import com.hll_sc_app.bean.goods.DepositProductsResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.bean.goods.SaleUnitNameBean;
import com.hll_sc_app.bean.goods.SkuCheckResp;
import com.hll_sc_app.bean.goods.SpecsStatusReq;

import java.util.ArrayList;
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
    Observable<BaseResp<CopyCategoryBean>> copyToCustomCategory(@Body BaseMapReq req);

    /**
     * 查询自定义分类
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100050")
    Observable<BaseResp<CustomCategoryResp>> queryCustomCategory(@Body BaseMapReq req);

    /**
     * 自定义分类新增/修改/删除
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100048")
    Observable<BaseResp<Object>> editCustomCategory(@Body BaseMapReq req);

    /**
     * 自定义分类排序
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100049")
    Observable<BaseResp<Object>> sortCustomCategory(@Body BaseReq<CustomCategorySortReq> req);

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

    /**
     * 查询商品单位列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100008")
    Observable<BaseResp<List<SaleUnitNameBean>>> querySaleUnitName(@Body BaseMapReq req);

    /**
     * 查询押金商品列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100019")
    Observable<BaseResp<DepositProductsResp>> queryDepositProducts(@Body BaseMapReq req);

    /**
     * 商品sku条码校验
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100087")
    Observable<BaseResp<SkuCheckResp>> checkSkuCode(@Body BaseMapReq req);

    /**
     * 行业标签查询接口
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100056")
    Observable<BaseResp<List<LabelBean>>> queryLabelList(@Body BaseMapReq req);

    /**
     * 商品属性公共列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100063")
    Observable<BaseResp<ArrayList<ProductAttrBean>>> queryProductAttrsList(@Body BaseMapReq req);
}
