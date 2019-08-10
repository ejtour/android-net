package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.InvoiceService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.invoice.InvoiceListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class Invoice {

    /**
     * 获取发票列表
     *
     * @param invoiceStatus 发票状态(1-已提交未开票,2-已开票,3-已驳回)
     * @param pageNum       页码
     * @param startTime     开始时间 yyyyMMdd
     * @param endTime       结束时间 yyyyMMdd
     */
    public static void getInvoiceList(int invoiceStatus, int pageNum, String startTime, String endTime, SimpleObserver<InvoiceListResp> observer) {
        String salesmanID = UserConfig.getSalesmanID();
        InvoiceService.INSTANCE
                .getInvoiceList(BaseMapReq.newBuilder()
                        .put("actionType", !TextUtils.isEmpty(salesmanID) ? "crm" : "supplier")
                        .put("invoiceStatus", String.valueOf(invoiceStatus))
                        .put("pageNum", String.valueOf(pageNum))
                        .put("startTime", invoiceStatus > 1 && TextUtils.isEmpty(salesmanID) ? startTime : null)
                        .put("endTime", invoiceStatus > 1 && TextUtils.isEmpty(salesmanID) ? endTime : null)
                        .put("pageSize", "20")
                        .put("userID", salesmanID)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
