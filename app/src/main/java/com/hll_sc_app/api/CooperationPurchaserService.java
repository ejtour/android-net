package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;
import com.hll_sc_app.bean.cooperation.DeliveryBean;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodResp;
import com.hll_sc_app.bean.cooperation.EmployeeBean;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 合作采购商
 *
 * @author zhuyingsong
 * @date 2019-07-17
 */
public interface CooperationPurchaserService {
    CooperationPurchaserService INSTANCE = HttpFactory.create(CooperationPurchaserService.class);

    /**
     * 查询合作餐企列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102007")
    Observable<BaseResp<CooperationPurchaserResp>> queryCooperationPurchaserList(@Body BaseMapReq req);

    /**
     * 删除合作餐企
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102022")
    Observable<BaseResp<Object>> delCooperationPurchaser(@Body BaseMapReq req);

    /**
     * 合作关系使用-集团搜索
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101041")
    Observable<BaseResp<List<PurchaserBean>>> queryPurchaserList(@Body BaseMapReq req);

    /**
     * 合作商详情获取
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102031")
    Observable<BaseResp<CooperationPurchaserDetail>> queryCooperationPurchaserDetail(@Body BaseMapReq req);

    /**
     * 查询支付方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101038")
    Observable<BaseResp<SettlementBean>> querySettlementList(@Body BaseMapReq req);

    /**
     * 查询配送方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101106")
    Observable<BaseResp<DeliveryBean>> queryDeliveryList(@Body BaseMapReq req);

    /**
     * 修改合作关系支付相关设置
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102033")
    Observable<BaseResp<Object>> editShopSettlement(@Body BaseReq<ShopSettlementReq> req);

    /**
     * 新增或删除合作门店
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102034")
    Observable<BaseResp<Object>> addCooperationShop(@Body BaseReq<CooperationShopReq> req);

    /**
     * 编辑合作门店
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102055")
    Observable<BaseResp<Object>> editCooperationShop(@Body BaseMapReq req);

    /**
     * 修改合作商人员信息
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102042")
    Observable<BaseResp<Object>> editShopEmployee(@Body BaseReq<ShopSettlementReq> req);

    /**
     * 供应商员工列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101080")
    Observable<BaseResp<List<EmployeeBean>>> queryEmployeeList(@Body BaseMapReq req);

    /**
     * 配送时段列表查询
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103067")
    Observable<BaseResp<DeliveryPeriodResp>> queryDeliveryPeriodList(@Body BaseMapReq req);
}
