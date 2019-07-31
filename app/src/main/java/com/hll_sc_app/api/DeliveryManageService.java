package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.delivery.DeliveryBean;
import com.hll_sc_app.bean.delivery.DeliveryCompanyReq;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.DeliveryPeriodResp;
import com.hll_sc_app.bean.delivery.ProvinceListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 配送设置
 *
 * @author zhuyingsong
 * @date 2019-07-12
 */
public interface DeliveryManageService {
    DeliveryManageService INSTANCE = HttpFactory.create(DeliveryManageService.class);

    /**
     * 查询配送方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101106")
    Observable<BaseResp<DeliveryBean>> queryDeliveryList(@Body BaseMapReq req);

    /**
     * 修改配送方式
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101104")
    Observable<BaseResp<Object>> editDeliveryType(@Body BaseMapReq req);

    /**
     * 修改三方配送公司状态
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101108")
    Observable<BaseResp<Object>> editDeliveryCompanyStatus(@Body BaseReq<DeliveryCompanyReq> req);

    /**
     * 添加三方配送公司
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101105")
    Observable<BaseResp<Object>> addDeliveryCompany(@Body BaseReq<DeliveryCompanyReq> req);

    /**
     * 配送时段列表查询
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103067")
    Observable<BaseResp<DeliveryPeriodResp>> queryDeliveryPeriodList(@Body BaseMapReq req);

    /**
     * 配送时段操作
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103068")
    Observable<BaseResp<Object>> editDeliveryPeriod(@Body BaseMapReq req);

    /**
     * 配送时效列表查询
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103064")
    Observable<BaseResp<DeliveryPeriodResp>> queryDeliveryAgeingList(@Body BaseMapReq req);

    /**
     * 配送时效操作
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103065")
    Observable<BaseResp<Object>> editDeliveryAgeing(@Body BaseMapReq req);

    /**
     * 起送金额列表查询
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103037")
    Observable<BaseResp<List<DeliveryMinimumBean>>> queryDeliveryMinimumList(@Body BaseMapReq req);

    /**
     * 删除起送金额分组
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103040")
    Observable<BaseResp<Object>> delDeliveryMinimum(@Body BaseMapReq req);

    /**
     * 起送金额明细查询 新增和修改时用
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103038")
    Observable<BaseResp<List<ProvinceListBean>>> queryDeliveryMinimumArea(@Body BaseMapReq req);

    /**
     * 新增、编辑起送金额
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:103039")
    Observable<BaseResp<Object>> editDeliveryMinimum(@Body BaseReq<DeliveryMinimumReq> req);
}
