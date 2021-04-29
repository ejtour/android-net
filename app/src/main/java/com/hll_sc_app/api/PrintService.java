package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.print.PrintPreviewResp;
import com.hll_sc_app.bean.print.PrintResp;
import com.hll_sc_app.bean.print.PrintStatusBean;
import com.hll_sc_app.bean.print.PrintTemplateResp;
import com.hll_sc_app.bean.print.PrinterBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
public interface PrintService {
    PrintService INSTANCE = HttpFactory.create(PrintService.class);

    @POST(HttpConfig.URL)
    @Headers("pv:111164")
    Observable<BaseResp<PrintTemplateResp>> queryMyTemplateList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111166")
    Observable<BaseResp<PrintTemplateResp>> querySysTemplateList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111163")
    Observable<BaseResp<Object>> addTemplate(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111167")
    Observable<BaseResp<Object>> enableTemplate(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111162")
    Observable<BaseResp<Object>> deleteTemplate(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111168")
    Observable<BaseResp<Object>> getTemplatePreviewData(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:111165")
    Observable<BaseResp<Object>> getPreviewData(@Body BaseMapReq req);

    @POST
    Observable<BaseResp<PrintPreviewResp>> getPreviewSourceCode(@Url String url, @Body Map<String, Object> req);

    @POST(HttpConfig.URL)
    @Headers("pv:113045")
    Observable<BaseResp<PrintResp>> print(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:113044")
    Observable<BaseResp<List<PrinterBean>>> queryPrinterList(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:113046")
    Observable<BaseResp<Object>> deletePrinter(@Body BaseMapReq req);

    @POST(HttpConfig.URL)
    @Headers("pv:113042")
    Observable<BaseResp<Object>> addPrinter(@Body BaseReq<PrinterBean> body);

    @POST(HttpConfig.URL)
    @Headers("pv:113043")
    Observable<BaseResp<PrintStatusBean>> queryPrintStatus(@Body BaseMapReq req);
}
