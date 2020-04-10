package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.contract.ContractMountBean;
import com.hll_sc_app.bean.contract.ContractProductListResp;

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

    /***
     * 修改合同状态
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102076")
    Observable<BaseResp<Object>> changeContract(@Body BaseMapReq req);



    @POST(HttpConfig.URL)
    @Headers("pv:102079")
    Observable<BaseResp<ContractProductListResp>> getAllProductList(@Body BaseMapReq req);


    /***
     * 查询合同统计数据
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:102080")
    Observable<BaseResp<ContractMountBean>> getContractMount(@Body BaseMapReq req);





}
