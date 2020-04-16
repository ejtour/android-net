package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.bill.BillActionReq;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillListResp;
import com.hll_sc_app.bean.bill.BillLogBean;
import com.hll_sc_app.bean.export.ExportResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */

public interface BillService {
    BillService INSTANCE = HttpFactory.create(BillService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:103060")
    Observable<BaseResp<BillListResp>> getBillList(@Body BaseMapReq req);


    @POST(HttpConfig.URL)
    @Headers("pv:103061")
    Observable<BaseResp<BillBean>> getBillDetail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103062")
    Observable<BaseResp<Object>> billAction(@Body BaseReq<BillActionReq> req);

    @POST(HttpConfig.URL)
    @Headers("pv:103063")
    Observable<BaseResp<ExportResp>> exportEmail(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103189")
    Observable<BaseResp<Object>> modifyAmount(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103190")
    Observable<BaseResp<List<BillLogBean>>> getBillLog(@Body BaseMapReq req);
}
