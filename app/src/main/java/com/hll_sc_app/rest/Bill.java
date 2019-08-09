package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.BillService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.bill.BillActionReq;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillListResp;
import com.hll_sc_app.bean.export.ExportResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */

public class Bill {
    /**
     * 获取对账单列表
     *
     * @param pageNum          页码
     * @param startTime        开始时间 yyyyMMdd
     * @param endTime          结束时间 yyyyMMdd
     * @param groupID          供应商ID 供应商与销售CRM必传
     * @param shopIDs          采购商店铺ID 多个用逗号连接
     * @param settlementStatus 结算状态 1-未结算 2-已结算,3-部分已结算
     */
    public static void getBillList(int pageNum,
                                   String startTime,
                                   String endTime,
                                   String groupID,
                                   String shopIDs,
                                   int settlementStatus,
                                   SimpleObserver<BillListResp> observer) {
        String salesmanID = UserConfig.getSalesmanID();
        BillService.INSTANCE
                .getBillList(BaseMapReq.newBuilder()
                        .put("flg", TextUtils.isEmpty(salesmanID) ? "1" : "3") // 标识必传：1-供应商，2-采购商，3-销售CRM
                        .put("startTime", startTime)
                        .put("endTime", endTime)
                        .put("groupID", groupID)
                        .put("shopIDs", shopIDs)
                        .put("salesmanID", salesmanID)
                        .put("settlementStatus", String.valueOf(settlementStatus))
                        .put("pageSize", "20")
                        .put("pageNum", String.valueOf(pageNum))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取对账单详情
     *
     * @param id 对账单id
     */
    public static void getBillDetail(String id, SimpleObserver<BillBean> observer) {
        BillService.INSTANCE
                .getBillDetail(BaseMapReq.newBuilder()
                        .put("flg", "1")
                        .put("id", id)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 对账单结算
     *
     * @param settleBillIDs 所有需要结算的结算单ID
     */
    public static void billAction(List<String> settleBillIDs, SimpleObserver<Object> observer) {
        BillActionReq req = new BillActionReq();
        req.setSettleBillIDs(settleBillIDs);
        BillService.INSTANCE
                .billAction(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 对账单导出
     *
     * @param sign             导出标识 1-对账单导出 2-对账单明细导出
     * @param email            绑定的邮箱，isBindEmail为1时必传
     * @param startTime        开始时间 yyyyMMdd
     * @param endTime          结束时间 yyyyMMdd
     * @param groupID          供应商ID
     * @param shopIDs          门店ID采购商店铺ID 多个用逗号连接
     * @param settlementStatus 订单结算状态 1-未结算 2-已结算
     */
    public static void exportEmail(int sign,
                                   String email,
                                   String startTime,
                                   String endTime,
                                   String groupID,
                                   String shopIDs,
                                   int settlementStatus,
                                   SimpleObserver<ExportResp> observer) {
        String salesmanID = UserConfig.getSalesmanID();
        BillService.INSTANCE
                .exportEmail(BaseMapReq.newBuilder()
                        .put("sign", String.valueOf(sign))
                        .put("flag", TextUtils.isEmpty(salesmanID) ? "2" : "3") // 1-采购商导出 2-供应商导出 3-销售CRM导出
                        .put("email", email)
                        .put("isBindEmail", TextUtils.isEmpty(email) ? "" : "1")
                        .put("startTime", startTime)
                        .put("endTime", endTime)
                        .put("groupID", groupID)
                        .put("salesmanID", salesmanID)
                        .put("shopIDs", shopIDs)
                        .put("settlementStatus", String.valueOf(settlementStatus))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
