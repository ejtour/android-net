package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AreaListReq;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.wallet.WalletInfoReq;
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
    Observable<BaseResp<WalletInfo>> queryAuthInfo(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101059")
    Observable<BaseResp<List<BankBean>>> getBankList(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101057")
    Observable<BaseResp<List<AreaInfo>>> queryAreaList(@Body BaseReq<AreaListReq> body);

    @POST(HttpConfig.URL)
    @Headers("pv:101130")
    Observable<BaseResp<WalletInfo>> getWalletInfo(@Body BaseReq<WalletInfoReq> body);

    /**
     * 新建主体
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101127")
    Observable<BaseResp<Object>> createSettlementObject(@Body BaseMapReq body);

    /**
     * 提交实名认证资料
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101128")
    Observable<BaseResp<Object>> submitAuthenInfo(@Body BaseReq<WalletInfo> body);

    /**
     * OCR图片识别
     *
     * @param body
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101131")
    Observable<BaseResp<OcrImageResp>> ocrImage(@Body BaseMapReq body);

    @POST(HttpConfig.URL)
    @Headers("pv:101198")
    Observable<BaseResp<Object>> rechargeReport(@Body BaseMapReq req);
}
