package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hll_sc_app.api.PrintService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.print.PrintPreviewResp;
import com.hll_sc_app.bean.print.PrintResp;
import com.hll_sc_app.bean.print.PrintStatusBean;
import com.hll_sc_app.bean.print.PrintTemplateResp;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.utils.DownloadUtil;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
public class Print {

    /**
     * 查询打印模板列表
     *
     * @param type 0-我的 1-系统预置
     */
    public static void queryPrintTemplateList(int type, SimpleObserver<PrintTemplateResp> observer) {
        BaseMapReq req = BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID()).create();
        Observable<BaseResp<PrintTemplateResp>> observable = type == 0 ? PrintService.INSTANCE.queryMyTemplateList(req) :
                PrintService.INSTANCE.querySysTemplateList(req);
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 模板操作
     *
     * @param actionType 0-加到我的 1-启用 2-删除
     * @param id         模板id
     * @param type       模板类型
     */
    public static void templateAction(int actionType, String id, int type, SimpleObserver<Object> observer) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("templateId", id)
                .put("templateType", type)
                .create();
        Observable<BaseResp<Object>> observable = actionType == 0 ? PrintService.INSTANCE.addTemplate(req) :
                actionType == 1 ? PrintService.INSTANCE.enableTemplate(req) :
                        actionType == 2 ? PrintService.INSTANCE.deleteTemplate(req) : null;
        if (observable == null) return;
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取模板预览的 h5源码
     *
     * @param templateID 模板id
     * @param subBillNo  订单号
     */
    public static void getPreviewSourceCode(String templateID, String subBillNo, SimpleObserver<PrintPreviewResp> observer) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("templateId", templateID);
        if (!TextUtils.isEmpty(subBillNo)) {
            builder.put("flag", 2)
                    .put("subBillNoList", subBillNo)
                    .put("templateType", 2)
                    .put("count", 0);
        }
        Observable<BaseResp<Object>> observable = TextUtils.isEmpty(subBillNo) ?
                PrintService.INSTANCE.getTemplatePreviewData(builder.create()) :
                PrintService.INSTANCE.getPreviewData(builder.create());
        observable
                .map(new Precondition<>())
                .flatMap((Function<Object, ObservableSource<BaseResp<PrintPreviewResp>>>) object -> {
                    Map<String, Object> map = new Gson().<Map<String, Object>>fromJson(new Gson().toJson(object), Map.class);
                    map.put("service", "HTTP_SERVICE_URL_SUPPLYCHAIN");
                    map.put("type", "post");
                    return PrintService.INSTANCE.getPreviewSourceCode(HttpConfig.getSupplyChainHost() + "/apiexport/shopMall/printShopMallPdfTemp",
                            map);
                })
                .map(new Precondition<>())
                .map(resp -> {
                    File file = new File(App.INSTANCE.getApplicationContext().getExternalCacheDir(), "preview.html");
                    DownloadUtil.getInstance().syncDownload("http://res.hualala.com/" + resp.getHtml(), file);
                    resp.setFileUrl(file.toURI().toURL().toString());
                    return resp;
                })
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> observer.startReq())
                .doFinally(observer::reqOver)
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 打印 pdf
     *
     * @param pdfUrl   pdf url
     * @param deviceID 打印机编码
     * @param copies 份数
     */
    public static void print(String pdfUrl, String html, int copies, String deviceID, SimpleObserver<PrintResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) return;
        PrintService.INSTANCE
                .print(BaseMapReq.newBuilder()
                        .put("billContent", pdfUrl)
                        .put("htmlContent", html)
                        .put("deviceID", deviceID)
                        .put("copies", copies)
                        .put("groupID", user.getGroupID())
                        .put("operator", user.getEmployeeName())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);

    }

    /**
     * 查询打印机列表
     */
    public static void queryPrinterList(SimpleObserver<List<PrinterBean>> observer) {
        PrintService.INSTANCE
                .queryPrinterList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 删除打印机
     *
     * @param deviceID 打印机编码
     */
    public static void deletePrinter(String deviceID, SimpleObserver<MsgWrapper<Object>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) return;
        PrintService.INSTANCE
                .deletePrinter(BaseMapReq.newBuilder()
                        .put("deviceID", deviceID)
                        .put("operator", user.getEmployeeName())
                        .put("groupID", user.getGroupID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 添加打印机
     */
    public static void addPrinter(PrinterBean bean, SimpleObserver<MsgWrapper<Object>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) return;
        BaseReq<PrinterBean> req = new BaseReq<>();
        bean.setOperator(user.getEmployeeName());
        bean.setGroupID(user.getGroupID());
        req.setData(bean);
        PrintService.INSTANCE
                .addPrinter(req)
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * @param billNo 打印单号
     */
    public static void queryPrintStatus(String billNo, BaseCallback<PrintStatusBean> observer) {
        PrintService.INSTANCE
                .queryPrintStatus(BaseMapReq.newBuilder()
                        .put("billNo", billNo)
                        .create())
                .map(new Precondition<>())
                .subscribe(observer);
    }
}
