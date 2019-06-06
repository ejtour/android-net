package com.hll_sc_app.base.service;

import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 账户
 *
 * @author zhuyingsong
 * @date 2019-06-04
 */
public interface AccountService {
    AccountService INSTANCE = HttpFactory.create(AccountService.class);

    /**
     * 获取验证码
     *
     * @param body body
     * @return Observable
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101003")
    Observable<BaseResp<Object>> getIdentifyCode(@Body BaseReq<GetIdentifyCodeReq> body);
}
