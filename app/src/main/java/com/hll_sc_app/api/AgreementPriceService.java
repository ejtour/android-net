package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.agreementprice.quotation.GroupInfoResp;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;
import com.hll_sc_app.bean.agreementprice.quotation.WarehouseDetailResp;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 协议价管理
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public interface AgreementPriceService {
    AgreementPriceService INSTANCE = HttpFactory.create(AgreementPriceService.class);

    /**
     * 查询报价单列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100023")
    Observable<BaseResp<QuotationResp>> queryQuotationList(@Body BaseMapReq req);

    /**
     * 查询报价单详情
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100024")
    Observable<BaseResp<QuotationDetailResp>> queryQuotationDetail(@Body BaseMapReq req);

    /**
     * 停用和失效报价单
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100025")
    Observable<BaseResp<Object>> disableQuotation(@Body BaseMapReq req);

    /**
     * 搜索合作采购商
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102028")
    Observable<BaseResp<List<PurchaserBean>>> queryCooperationPurchaserList(@Body BaseMapReq req);

    /**
     * 搜索合作采购商门店
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102017")
    Observable<BaseResp<List<PurchaserShopBean>>> queryCooperationPurchaserShopList(@Body BaseMapReq req);

    /**
     * 查询签约关系列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101045")
    Observable<BaseResp<GroupInfoResp>> queryCooperationGroupList(@Body BaseMapReq req);

    /**
     * 获取代仓签约详情成功
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101044")
    Observable<BaseResp<WarehouseDetailResp>> queryCooperationWarehouseDetail(@Body BaseMapReq req);
}
