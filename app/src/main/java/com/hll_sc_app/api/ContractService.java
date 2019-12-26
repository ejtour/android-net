package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.contract.ContractListResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ContractService {
    ContractService INSTANCE = HttpFactory.create(ContractService.class);

    /**
     * 查询合同列表
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102075")
    Observable<BaseResp<ContractListResp>> queryContractList(@Body BaseMapReq req);

    /**
     * 添加合同列表
     *
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102074")
    Observable<BaseResp<Object>> addContract(@Body BaseMapReq req);


}
