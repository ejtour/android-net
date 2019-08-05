package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.warehouse.GroupDetail;
import com.hll_sc_app.bean.warehouse.WarehouseListResp;

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
public interface WarehouseManageService {
    WarehouseManageService INSTANCE = HttpFactory.create(WarehouseManageService.class);

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
     * 解除签约关系或者放弃代仓
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101048")
    Observable<BaseResp<Object>> delWarehouse(@Body BaseMapReq req);

    /**
     * 查询签约关系列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101045")
    Observable<BaseResp<WarehouseListResp>> queryWarehouseList(@Body BaseMapReq req);
}
