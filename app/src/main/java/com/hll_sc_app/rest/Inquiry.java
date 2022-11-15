package com.hll_sc_app.rest;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.hll_sc_app.api.InquiryService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

public class Inquiry {

    /**
     * 查询询价列表
     *
     * @param status  询价状态 0-全部 1-询价中 2-已报价 3-已过期
     * @param pageNum 页码
     */
    public static void queryList(int status, int pageNum, SimpleObserver<SingleListResp<InquiryBean>> observer) {
        InquiryService.INSTANCE
                .queryList(BaseMapReq.newBuilder()
                        .put("enquiryStatus", status == 0 ? "" : String.valueOf(status))
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询询价详情
     *
     * @param id 询价id
     */
    public static void queryDetail(String id, SimpleObserver<InquiryBean> observer) {
        InquiryService.INSTANCE
                .queryDetail(BaseMapReq.newBuilder()
                        .put("enquiryID", id)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 提交询价
     *
     * @param bean 询价单
     */
    public static void submit(InquiryBean bean, SimpleObserver<Object> observer) {
        InquiryService.INSTANCE
                .submit(new BaseReq<>(bean))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询三方商品绑定
     *
     * @param id 询价id
     */
    public static void bindResult(String id, SimpleObserver<InquiryBindResp> observer) {
        InquiryService.INSTANCE
                .bindResult(BaseMapReq.newBuilder()
                        .put("enquiryID", id)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
