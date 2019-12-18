package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.stockmanage.BusinessTypeBean;
import com.hll_sc_app.bean.stockmanage.CustomerSendManageListResp;
import com.hll_sc_app.bean.stockmanage.RemoveStockCheckSettingReq;
import com.hll_sc_app.bean.stockmanage.StockLogResp;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderBean;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 库存管理接口
 */
public interface StockManageService {
    StockManageService INSTANCE = HttpFactory.create(StockManageService.class);

    /**
     * 获取供应商员工列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100119")
    Observable<BaseResp<StorehouseListResp>> getStoreHouseList(@Body BaseMapReq req);

    /**
     * 新增和修改仓库信息
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100118")
    Observable<BaseResp<Object>> saveStoreHouseInfo(@Body BaseReq<StorehouseListResp.Storehouse> req);

    /**
     * 获取交易类型
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100128")
    Observable<BaseResp<List<BusinessTypeBean>>> queryBusinessType(@Body BaseReq<Object> req);

    /**
     * 库存流水列表查询
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100125")
    Observable<BaseResp<StockLogResp>> fetchStockLogs(@Body BaseMapReq req);


    /**
     * 客户发货仓库管理列表
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102053")
    Observable<BaseResp<CustomerSendManageListResp>> queryCustomerSendManageListResp(@Body BaseMapReq req);


    /**
     * 商品库存校验设置
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100110")
    Observable<BaseResp<Object>> changeStockCheckSetting(@Body BaseReq<RemoveStockCheckSettingReq> req);

    /**
     * 供应链采购单列表查询
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103151")
    Observable<BaseResp<SingleListResp<PurchaserOrderBean>>> queryPurchaserOrderList(@Body BaseMapReq req);

    /**
     * 供应链采购单明细查询
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103152")
    Observable<BaseResp<PurchaserOrderDetailResp>> querySupplyChainPurchaserOrderDetail(@Body BaseMapReq req);


    /**
     * 供应链供应商列表查询
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101112")
    Observable<BaseResp<PurchaserOrderSearchResp>> querySupplyChainGroupList(@Body BaseMapReq req);

}
