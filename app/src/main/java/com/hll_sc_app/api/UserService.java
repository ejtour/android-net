package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.account.UnbindGroupReq;
import com.hll_sc_app.bean.account.UnbindMainAccountReq;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.groupInfo.GroupInfoReq;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.refundtime.RefundTimeResp;
import com.hll_sc_app.bean.refundtime.SetRefundTimeReq;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.hll_sc_app.bean.user.InviteCodeResp;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.bean.user.RegisterReq;
import com.hll_sc_app.bean.user.RemindReq;
import com.hll_sc_app.bean.user.RemindResp;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;
import com.hll_sc_app.bean.user.TaxSaveReq;

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
     * 请求集团信息
     *
     * @param body 请求
     * @return observable
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101009")
    Observable<BaseResp<GroupInfoResp>> getGroupInfo(@Body BaseReq<GroupInfoReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101010")
    Observable<BaseResp<Object>> updateGroupInfo(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101063")
    Observable<BaseResp<Object>> reqCertify(@Body BaseReq<CertifyReq> body);

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


    /**
     * 主账号解绑
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101035")
    Observable<BaseResp<Object>> unBindMainAccount(@Body BaseReq<UnbindMainAccountReq> body);


    /**
     * 子账号解绑
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101036")
    Observable<BaseResp<Object>> unbindGroup(@Body BaseReq<UnbindGroupReq> body);

    /**
     * 获取退货时效
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101065")
    Observable<BaseResp<RefundTimeResp>> listRefundTime(@Body BaseMapReq body);


    /**
     * 设置退货时效
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101066")
    Observable<BaseResp<RefundTimeResp>> setRefundTime(@Body BaseReq<SetRefundTimeReq> body);

    /**
     * 保存税率
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:100072")
    Observable<BaseResp<Object>> saveTaxRate(@Body BaseReq<TaxSaveReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:100085")
    Observable<BaseResp<Object>> saveSpecialTaxRate(@Body BaseReq<SpecialTaxSaveReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:100086")
    Observable<BaseResp<SingleListResp<SpecialTaxBean>>> querySpecialTax(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103091")
    Observable<BaseResp<RemindResp>> queryRemind(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103090")
    Observable<BaseResp<Object>> updateRemind(@Body BaseReq<RemindReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:100155")
    Observable<BaseResp<SingleListResp<PurchaseTemplateBean>>> queryPurchaseTemplate(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:101091")
    Observable<BaseResp<InviteCodeResp>> queryInviteCode(@Body BaseMapReq req);
}
