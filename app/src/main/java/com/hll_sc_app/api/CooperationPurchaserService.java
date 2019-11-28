package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.cooperation.ChangeGroupParamReq;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserBean;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.paymanage.PayResp;
import com.hll_sc_app.bean.shop.SupplierShopBean;
import com.hll_sc_app.bean.shop.SupplierShopUpdateReq;
import com.hll_sc_app.bean.staff.EmployeeBean;

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
     * 合作商信息编辑
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102032")
    Observable<BaseResp<Object>> editCooperationPurchaser(@Body BaseMapReq req);

    /**
     * 添加合作餐企
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102009")
    Observable<BaseResp<Object>> addCooperationPurchaser(@Body BaseMapReq req);

    /**
     * 接口名称 编辑合作关系拜访维护级别/客户级别
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102054")
    Observable<BaseResp<Object>> editCooperationPurchaserLevel(@Body BaseMapReq req);

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
     * 修改支付方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101039")
    Observable<BaseResp<Object>> editSettlement(@Body BaseMapReq req);

    /**
     * 修改支付方式列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101090")
    Observable<BaseResp<Object>> editSettlementMethod(@Body BaseMapReq req);

    /**
     * 查询默认支付方式列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101088")
    Observable<BaseResp<PayResp>> querySettlementMethodList(@Body BaseMapReq req);


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
     * 获取第三方申请审核列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103701")
    Observable<BaseResp<ThirdPartyPurchaserResp>> queryThirdPartPurchaserList(@Body BaseMapReq req);

    /**
     * 根据申请记录id查询第三方采购商审核记录详情
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103702")
    Observable<BaseResp<ThirdPartyPurchaserBean>> queryThirdPartPurchaserDetail(@Body BaseMapReq req);

    /**
     * 采购商申请状态变更
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103703")
    Observable<BaseResp<Object>> editThirdPartPurchaserDetail(@Body BaseMapReq req);

    /**
     * 获取供应商店铺
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102023")
    Observable<BaseResp<List<SupplierShopBean>>> listSupplierShop(@Body BaseMapReq req);

    /**
     * 更新供应商店铺
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102025")
    Observable<BaseResp<Object>> updateSupplierShop(@Body BaseReq<SupplierShopUpdateReq> req);

    /**
     * 查询集团列表-在报表中心-客户收货统计过滤中使用
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102063")
    Observable<BaseResp<QueryGroupListResp>> queryGroupList(@Body BaseMapReq req);


    /**
     * 修改集团参数
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102070")
    Observable<BaseResp<QueryGroupListResp>> changeGroupParams(@Body BaseReq<ChangeGroupParamReq> req);


}
