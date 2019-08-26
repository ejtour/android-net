package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.bean.inspection.InspectionResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public interface InspectionService {
    InspectionService INSTANCE = HttpFactory.create(InspectionService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103155")
    Observable<BaseResp<InspectionResp>> getInspectionList(@Body BaseMapReq req);
    @POST(HttpConfig.URL)
    @Headers("pv:103156")
    Observable<BaseResp<InspectionBean>> getInspectionDetail(@Body BaseMapReq req);
}
