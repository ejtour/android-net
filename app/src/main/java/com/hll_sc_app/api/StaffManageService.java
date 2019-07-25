package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.staff.StaffBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 员工管理
 *
 * @author zhuyingsong
 * @date 2019-07-25
 */
public interface StaffManageService {
    StaffManageService INSTANCE = HttpFactory.create(StaffManageService.class);

    /**
     * 获取供应商员工列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101025")
    Observable<BaseResp<List<StaffBean>>> queryStaffList(@Body BaseMapReq req);

    /**
     * 查询员工数量
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101103")
    Observable<BaseResp<StaffBean>> queryStaffNum(@Body BaseMapReq req);
}
