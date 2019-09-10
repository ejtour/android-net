package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderResp;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author chukun
 * @date 2019/09/10
 * 库存管理接口
 */
public interface StockService {

    StockService INSTANCE = HttpFactory.create(StockService.class);

    /**
     * 供应链采购单列表查询
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103151")
    Observable<BaseResp<PurchaserOrderResp>> querySupplyChainPurchaserOrderList(@Body BaseReq<PurchaserOrderReq> req);

    /**
     * 供应链采购单明细查询
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103152")
    Observable<BaseResp<PurchaserOrderDetailResp>> querySupplyChainPurchaserOrderDetail(@Body BaseMapReq req);
}
