package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.hll_sc_app.bean.user.RegisterReq;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    /**
     * 修改供应商员工邮箱
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101095")
    Observable<BaseResp<Object>> bindEmail(@Body BaseMapReq req);

    /**
     * 供应商登录找回密码
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101012")
    Observable<BaseResp<Object>> find(@Body BaseMapReq req);

    /**
     * 修改密码
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101037")
    Observable<BaseResp<Object>> changePassword(@Body BaseMapReq req);

    /**
     * 供应商集团注册(简化版)
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101008")
    Observable<BaseResp<Object>> register(@Body BaseReq<RegisterReq> req);

    /**
     * 获取分类列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100010")
    Observable<BaseResp<CategoryResp>> queryCategory(@Body BaseMapReq req);

    /**
     * 退出登录
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101013")
    Observable<BaseResp<Object>> logout(@Body BaseMapReq req);

    /**
     * 图片上传
     *
     * @param file req
     * @return resp
     */
    @Headers("Origin: */*")
    @Multipart
    @POST("/upload!mobileClient.action")
    Observable<String> imageUpload(@Part() MultipartBody.Part file);


    /**
     * 设置页面里的查询集团参数
     *
     * @param body 请求参数
     * @return result
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101032")
    Observable<BaseResp<List<GroupParame>>> queryGroupParameterInSetting(@Body BaseReq<GroupParameReq> body);


    /**
     * 设置页面里更改集团参数
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101033")
    Observable<BaseResp<Object>> changeGroupParameterInSetting(@Body BaseReq<GroupParame> body);

}
