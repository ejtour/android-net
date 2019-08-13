package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public interface CommonService {
    CommonService INSTANCE = HttpFactory.create(CommonService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:102028")
    Observable<BaseResp<List<PurchaserBean>>> queryPurchaserList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:102017")
    Observable<BaseResp<List<PurchaserShopBean>>> queryPurchaserShopList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111063")
    Observable<BaseResp<SalesVolumeResp>> querySalesVolume(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103142")
    Observable<BaseResp<ExportResp>> exportExcel(@Body BaseReq<ExportReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:102046")
    Observable<BaseResp<CooperationShopListResp>> listCooperationShop(@Body BaseMapReq req);
}
