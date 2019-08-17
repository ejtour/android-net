package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.InvoiceService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.bean.invoice.InvoiceHistoryResp;
import com.hll_sc_app.bean.invoice.InvoiceListResp;
import com.hll_sc_app.bean.invoice.InvoiceMakeReq;
import com.hll_sc_app.bean.invoice.InvoiceMakeResp;
import com.hll_sc_app.bean.invoice.InvoiceOrderResp;
import com.hll_sc_app.bean.invoice.ReturnRecordResp;
import com.hll_sc_app.citymall.util.CommonUtils;
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

    /**
     * 获取关联订单列表
     *
     * @param startTime 开始时间 yyyyMMdd
     * @param endTime   结束时间 yyyyMMdd
     * @param shopID    店铺id
     */
    public static void getRelevanceOrderList(int pageNum, String startTime, String endTime, String shopID, SimpleObserver<InvoiceOrderResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        InvoiceService.INSTANCE
                .getRelevanceOrderList(BaseMapReq.newBuilder()
                        .put("subBillDateStart", startTime)
                        .put("subBillDateEnd", endTime)
                        .put("groupID", user.getGroupID())
                        .put("shopID", shopID)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("statstFlag", "1")
                        .put("salesmanID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取发票填写历史
     *
     * @param titleType 发票抬头类型(1-企业,2-个人/事业单位)
     */
    public static void getInvoiceHistory(int titleType, SimpleObserver<InvoiceHistoryResp> observer) {
        InvoiceService.INSTANCE
                .getInvoiceHistory(BaseMapReq.newBuilder()
                        .put("pageSize", "3")
                        .put("pageNum", "1")
                        .put("titleType", String.valueOf(titleType))
                        .put("userID", GreenDaoUtils.getUser().getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 开发票
     */
    public static void makeInvoice(InvoiceMakeReq req, SimpleObserver<InvoiceMakeResp> observer) {
        InvoiceService.INSTANCE
                .makeInvoice(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取开票详情
     *
     * @param id 发票id
     */
    public static void getInvoiceDetail(String id, SimpleObserver<InvoiceBean> observer) {
        InvoiceService.INSTANCE
                .getInvoiceDetail(BaseMapReq.newBuilder()
                        .put("id", id).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 变更发票状态
     *
     * @param actionType     操作类型，1-确认开票，2-驳回
     * @param id             发票id
     * @param invoiceNO      发票号，以逗号拼接
     * @param invoicePrice   发票金额
     * @param invoiceVoucher 发票凭证
     * @param rejectReason   驳回原因
     */
    public static void doAction(int actionType,
                                String id,
                                String invoiceNO,
                                double invoicePrice,
                                String invoiceVoucher,
                                String rejectReason,
                                SimpleObserver<MsgWrapper<Object>> observer) {
        InvoiceService.INSTANCE
                .doAction(BaseMapReq.newBuilder()
                        .put("actionType", actionType == 1 ? "invoice" : "reject")
                        .put("id", id)
                        .put("invoiceNO", invoiceNO)
                        .put("invoicePrice", CommonUtils.formatNumber(invoicePrice))
                        .put("invoiceVoucher", invoiceVoucher)
                        .put("rejectReason", rejectReason)
                        .put("userID", GreenDaoUtils.getUser().getEmployeeID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 发票回款结算
     *
     * @param returnRecordID 回款记录ID
     */
    public static void settle(String returnRecordID, SimpleObserver<MsgWrapper<Object>> observer) {
        InvoiceService.INSTANCE
                .settle(BaseMapReq.newBuilder()
                        .put("returnRecordID", returnRecordID)
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 新增或修改回款记录
     *
     * @param action        0-新增 1-修改
     * @param id            回款记录id
     * @param invoiceID     发票ID
     * @param returnDate    回款日期yyyyMMdd
     * @param returnMoney   回款金额
     * @param returnPayType 回款方式1现金、2银行转账、3支票、4其它
     */
    public static void updateReturnRecord(int action, String id, String invoiceID, String returnDate, double returnMoney, String returnPayType, SimpleObserver<ReturnRecordResp> observer) {
        InvoiceService.INSTANCE
                .updateReturnRecord(BaseMapReq.newBuilder()
                        .put("action", String.valueOf(action))
                        .put("id", id)
                        .put("invoiceID", invoiceID)
                        .put("returnDate", returnDate)
                        .put("returnMoney", CommonUtils.formatMoney(returnMoney))
                        .put("returnPayType", returnPayType)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /**
     * 修改发票信息
     *
     * @param id             发票id
     * @param invoiceNO      发票号
     * @param invoiceVoucher 发票凭证
     */
    public static void modifyInvoiceInfo(String id, String invoiceNO, String invoiceVoucher, SimpleObserver<MsgWrapper<Object>> observer) {
        InvoiceService.INSTANCE
                .modifyInvoiceInfo(BaseMapReq.newBuilder()
                        .put("id", id)
                        .put("invoiceNO", invoiceNO)
                        .put("invoiceVoucher", invoiceVoucher, true)
                        .put("userID", GreenDaoUtils.getUser().getEmployeeID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取发票对应的关联订单列表
     *
     * @param id
     */
    public static void reqRelevanceOrderList(String id, int pageNum, SimpleObserver<InvoiceOrderResp> observer) {
        InvoiceService.INSTANCE
                .reqRelevanceOrderList(BaseMapReq.newBuilder()
                        .put("invoiceID", id)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
