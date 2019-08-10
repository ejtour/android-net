package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public interface WalletService {
    WalletService INSTANCE = HttpFactory.create(WalletService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:101053")
    Observable<BaseResp<WalletStatusResp>> queryWalletStatus(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:103075")
    Observable<BaseResp<DetailsListResp>> getWalletDetailsList(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:103076")
    Observable<BaseResp<RechargeResp>> recharge(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101058")
    Observable<BaseResp<Object>> withdraw(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101062")
    Observable<BaseResp<AuthInfo>> queryAuthInfo(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101057")
    Observable<BaseResp<List<AreaInfo>>> queryAreaList(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101054")
    Observable<BaseResp<Object>> createAccount(@Body BaseReq<AuthInfo> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101055")
    Observable<BaseResp<Object>> authAccount(@Body BaseReq<AuthInfo> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101059")
    Observable<BaseResp<List<BankBean>>> getBankList(@Body BaseMapReq body);
}
