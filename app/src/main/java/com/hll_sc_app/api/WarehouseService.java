package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.warehouse.GroupDetail;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.bean.warehouse.ShopParameterBean;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.bean.warehouse.WarehouseListResp;
import com.hll_sc_app.bean.warehouse.WarehousePurchaserEditReq;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 代仓管理
 *
 * @author zhuyingsong
 * @date 2019-08-02
 */
public interface WarehouseService {
    WarehouseService INSTANCE = HttpFactory.create(WarehouseService.class);

    /**
     * 推荐代仓公司
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101068")
    Observable<BaseResp<List<PurchaserBean>>> queryRecommendWarehouseList(@Body BaseMapReq req);

    /**
     * 查询集团详情
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:104107")
    Observable<BaseResp<GroupDetail>> queryGroupDetail(@Body BaseMapReq req);

    /**
     * 采购商集团搜索
     *
     * @param body req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101024")
    Observable<BaseResp<List<PurchaserBean>>> queryPurchaserList(@Body BaseMapReq body);

    /**
     * 申请代仓
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101042")
    Observable<BaseResp<Object>> addWarehouse(@Body BaseMapReq req);

    /**
     * 同意或者拒接签约
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101043")
    Observable<BaseResp<Object>> agreeOrRefuseWarehouse(@Body BaseMapReq req);

    /**
     * 获取代仓签约详情成功
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101044")
    Observable<BaseResp<WarehouseDetailResp>> queryCooperationWarehouseDetail(@Body BaseMapReq req);

    /**
     * 查询签约关系列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101045")
    Observable<BaseResp<WarehouseListResp>> queryWarehouseList(@Body BaseMapReq req);

    /**
     * 解除签约关系或者放弃代仓
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101048")
    Observable<BaseResp<Object>> delWarehouse(@Body BaseMapReq req);

    /**
     * 查询代仓门店
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101102")
    Observable<BaseResp<List<ShopParameterBean>>> queryWarehouseShop(@Body BaseMapReq req);

    /**
     * 编辑代仓门店
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101101")
    Observable<BaseResp<Object>> editWarehouseShop(@Body BaseMapReq req);

    /**
     * 编辑货主信息-退货审核
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101111")
    Observable<BaseResp<Object>> editWarehouseParameter(@Body BaseMapReq req);

    /**
     * 我是货主-新签代仓公司
     *
     * @param body req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101050")
    Observable<BaseResp<WarehouseListResp>> queryShipperWarehouseList(@Body BaseMapReq body);

    /**
     * 代仓查询合作采购商列表
     *
     * @param body req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102047")
    Observable<BaseResp<ShipperShopResp>> queryWarehousePurchaserList(@Body BaseMapReq body);

    /**
     * 代仓编辑合作采购商
     *
     * @param body req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102048")
    Observable<BaseResp<Object>> editWarehousePurchaser(@Body BaseReq<WarehousePurchaserEditReq> body);

    /**
     * 代仓查询合作关系店铺列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102049")
    Observable<BaseResp<Object>> queryWarehousePurchaserShopList(@Body BaseMapReq req);
}
