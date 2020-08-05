package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.CustomCategorySortReq;
import com.hll_sc_app.bean.goods.GoodsAddBatchReq;
import com.hll_sc_app.bean.goods.GoodsAddBatchResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsInvWarnReq;
import com.hll_sc_app.bean.goods.GoodsInvWarnResp;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.GoodsRelevanceResp;
import com.hll_sc_app.bean.goods.GoodsStickReq;
import com.hll_sc_app.bean.goods.GoodsTemplateResp;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.bean.goods.ProductBrandResp;
import com.hll_sc_app.bean.goods.RelevancePurchaserResp;
import com.hll_sc_app.bean.goods.SaleUnitNameBean;
import com.hll_sc_app.bean.goods.SkuCheckResp;
import com.hll_sc_app.bean.goods.SkuProductsResp;
import com.hll_sc_app.bean.goods.SpecsStatusReq;
import com.hll_sc_app.bean.orientation.OrientationDetailRes;
import com.hll_sc_app.bean.orientation.OrientationListRes;
import com.hll_sc_app.bean.orientation.OrientationSetReq;
import com.hll_sc_app.bean.user.CategoryItem;

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
     * 获取商品详情
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100054")
    Observable<BaseResp<GoodsBean>> getGoodsDetail(@Body BaseMapReq req);

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

    @POST(HttpConfig.URL)
    @Headers("pv:112019")
    Observable<BaseResp<SingleListResp<GoodsBean>>> queryDiscountGoodsList(@Body BaseMapReq req);

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
     * 查询sku商品列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100019")
    Observable<BaseResp<SkuProductsResp>> querySkuProducts(@Body BaseMapReq req);

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

    /**
     * 查询审核通过品牌
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100136")
    Observable<BaseResp<List<String>>> queryProductBrandList(@Body BaseMapReq req);

    /**
     * 查询品牌列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100009")
    Observable<BaseResp<ProductBrandResp>> queryAllProductBrandList(@Body BaseMapReq req);

    /**
     * 商品模板查询接口
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100053")
    Observable<BaseResp<GoodsTemplateResp>> queryGoodsTemplateList(@Body BaseMapReq req);

    /**
     * 删除品牌申请
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100137")
    Observable<BaseResp<Object>> delProductBrandReq(@Body BaseMapReq req);

    /**
     * 提交品牌申请
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100091")
    Observable<BaseResp<Object>> addProductBrand(@Body BaseMapReq req);

    /**
     * 商品添加
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100003")
    Observable<BaseResp<Object>> addProduct(@Body BaseReq<GoodsBean> req);

    /**
     * 商品修改
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100004")
    Observable<BaseResp<Object>> editProduct(@Body BaseReq<GoodsBean> req);

    /**
     * 商品库批量新增商品
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100051")
    Observable<BaseResp<GoodsAddBatchResp>> addProductBatch(@Body BaseReq<GoodsAddBatchReq> req);

    /**
     * 查询自定义商品分类列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100018")
    Observable<BaseResp<CustomCategoryResp>> queryCustomCategory2Top(@Body BaseMapReq req);

    /**
     * 商品置顶
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100106")
    Observable<BaseResp<Object>> goods2Top(@Body BaseReq<GoodsStickReq> req);

    /**
     * 获取仓库下拉列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100121")
    Observable<BaseResp<List<HouseBean>>> queryHouseList(@Body BaseMapReq req);

    /**
     * 获取商品库存列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100126")
    Observable<BaseResp<GoodsInvWarnResp>> queryGoodsInvList(@Body BaseMapReq req);

    /**
     * 库存预警值设置
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100142")
    Observable<BaseResp<Object>> setGoodsInvWarnValue(@Body BaseReq<GoodsInvWarnReq> req);

    /**
     * 商品列表导出
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100062")
    Observable<BaseResp<ExportResp>> exportGoodsList(@Body BaseMapReq req);

    /**
     * 查询商品关联的集团列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103709")
    Observable<BaseResp<RelevancePurchaserResp>> queryGoodsRelevancePurchaserList(@Body BaseMapReq req);

    /**
     * 查询未关联商品列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103710")
    Observable<BaseResp<GoodsRelevanceResp>> queryGoodsUnRelevanceList(@Body BaseMapReq req);

    /**
     * 查询关联商品的列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103711")
    Observable<BaseResp<GoodsRelevanceResp>> queryGoodsRelevanceList(@Body BaseMapReq req);

    /**
     * 解除关联
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103713")
    Observable<BaseResp<Object>> removeGoodsRelevance(@Body BaseMapReq req);

    /**
     * 关联 和 重新关联
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103712")
    Observable<BaseResp<Object>> addGoodsRelevance(@Body BaseMapReq req);

    /**
     * 统一导出文件
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:110027")
    Observable<BaseResp<ExportResp>> exportRecord(@Body BaseReq<ExportReq> req);

    /**
     * 获取定向售卖详情
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100080")
    Observable<BaseResp<OrientationDetailRes>> getOrientationDetail(@Body BaseMapReq req);

    /**
     * 获取定向售卖列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100079")
    Observable<BaseResp<OrientationListRes>> getOrientationList(@Body BaseMapReq req);

    /**
     * 获取定向售卖列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100078")
    Observable<BaseResp<Object>> setOrientation(@Body BaseReq<OrientationSetReq> req);

    /**
     * 删除定向售卖分组
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100081")
    Observable<BaseResp<Object>> delOrientation(@Body BaseMapReq req);

    /**
     * 查询供应链商品分类
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100195")
    Observable<BaseResp<SingleListResp<CategoryItem>>> getSupplyChainCategory(@Body BaseMapReq req);


    /**
     * 搜索供应链商品
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100196")
    Observable<BaseResp<SingleListResp<GoodsBean>>> searchSupplyChainGoods(@Body BaseMapReq req);
}
