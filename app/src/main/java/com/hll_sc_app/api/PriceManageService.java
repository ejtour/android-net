package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 售价设置
 *
 * @author zhuyingsong
 * @date 2019-07-12
 */
public interface PriceManageService {
    PriceManageService INSTANCE = HttpFactory.create(PriceManageService.class);

    /**
     * 添加报价单
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100020")
    Observable<BaseResp<Object>> addQuotation(@Body BaseReq<QuotationReq> req);
}
