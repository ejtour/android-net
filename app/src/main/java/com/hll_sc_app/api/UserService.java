package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 用户中心
 *
 * @author zhuyingsong
 * @date 2019-05-31
 */
public interface UserService {
    UserService INSTANCE = HttpFactory.create(UserService.class);

    /**
     * 登录
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101002")
    Observable<BaseResp<LoginResp>> login(@Body BaseMapReq req);
}
