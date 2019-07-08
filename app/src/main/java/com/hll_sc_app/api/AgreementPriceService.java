package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;

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
}
