package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.AfterSalesService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aftersales.AfterSalesActionReq;
import com.hll_sc_app.bean.aftersales.AfterSalesActionResp;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyReq;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyResp;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.aftersales.AfterSalesReasonBean;
import com.hll_sc_app.bean.aftersales.AfterSalesVerifyResp;
import com.hll_sc_app.bean.aftersales.CommitComplainProductBean;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;
import com.hll_sc_app.bean.aftersales.PurchaserListResp;
import com.hll_sc_app.bean.aftersales.RefundMethodBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AfterSales {
    /**
     * 请求售后详情
     *
     * @param refundBillID 订单id
     */
    public static void requestAfterSalesDetail(String refundBillID,
                                               SimpleObserver<List<AfterSalesBean>> observer) {
        requestAfterSalesList(null, null, null,
                null, null, refundBillID, null,
                0, 0, observer);
    }

    /**
     * 请求售后列表
     *
     * @param startTime        开始时间 yyyyMMdd
     * @param endTime          结束时间 yyyyMMdd
     * @param refundBillStatus 订单状态
     * @param purchaserShopID  采购商门店id
     * @param purchaserID      采购商id
     * @param refundBillID     订单id
     * @param refundBillNo    订单no
     * @param sourceType       0：全部 1：自由退货 2：快速退货
     * @param pageNum          页码
     */
    public static void requestAfterSalesList(
            String startTime, String endTime,
            Integer refundBillStatus,
            String purchaserShopID,
            String purchaserID,
            String refundBillID,
            String refundBillNo,
            int sourceType,
            int pageNum,
            SimpleObserver<List<AfterSalesBean>> observer) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("flag", UserConfig.crm() ? "2" : "1")
                .put("groupID", UserConfig.getGroupID());
        if (refundBillID != null) {
            builder.put("refundBillID", refundBillID)
                    .put("sign", "2");
        } else if (refundBillNo != null) {
            builder.put("refundBillNo", refundBillNo)
                    .put("sign", "2");
        } else
            builder.put("pageNum", String.valueOf(pageNum))
                    .put("pageSize", "20")
                    .put("refundBillStatus", refundBillStatus != null ? String.valueOf(refundBillStatus) : null)
                    .put("purchaserID", purchaserID)
                    .put("purchaserShopID", purchaserShopID)
                    .put("sourceType", String.valueOf(sourceType))
                    .put("startTime", startTime)
                    .put("endTime", endTime);
        AfterSalesService.INSTANCE
                .getAfterSalesList(builder.create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 请求售后详情
     *
     * @param refundBillNo 订单no
     */
    public static void requestAfterSalesDetailByNo(String refundBillNo,
                                                   SimpleObserver<List<AfterSalesBean>> observer) {
        requestAfterSalesList(null, null, null,
                null, null, null, refundBillNo,
                0, 0, observer);
    }

    /**
     * @param startTime        开始时间 yyyyMMdd
     * @param endTime          结束时间 yyyyMMdd
     * @param refundBillStatus 订单状态
     * @param purchaserShopID  采购商门店id
     * @param purchaserID      采购商id
     * @param sourceType       0：全部 1：自由退货 2：快速退货
     * @param email            邮箱
     */
    public static void exportAfterSalesOrder(String startTime,
                                             String endTime,
                                             Integer refundBillStatus,
                                             String purchaserShopID,
                                             String purchaserID,
                                             int sourceType,
                                             String email,
                                             SimpleObserver<ExportResp> observer) {
        AfterSalesService.INSTANCE
                .exportAfterSalesOrder(BaseMapReq.newBuilder()
                        .put("startTime", startTime)
                        .put("endTime", endTime)
                        .put("groupID", UserConfig.getGroupID())
                        .put("refundBillStatus", refundBillStatus != null ? String.valueOf(refundBillStatus) : null)
                        .put("purchaserShopID", purchaserShopID)
                        .put("purchaserID", purchaserID)
                        .put("sourceType", String.valueOf(sourceType))
                        .put("email", email)
                        .put("isBindEmail", TextUtils.isEmpty(email) ? "" : "1")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 请求售后采购商和门店列表
     */
    public static void getPurchaserList(SimpleObserver<PurchaserListResp> observer) {
        AfterSalesService.INSTANCE
                .getPurchaserShopList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 修改单价
     *
     * @param price        单价
     * @param detailsID    退货单id
     * @param refundBillID 退货单明细id
     */
    public static void modifyUnitPrice(String price, String detailsID, String refundBillID, SimpleObserver<Object> observer) {
        AfterSalesService.INSTANCE
                .modifyUnitPrice(BaseMapReq.newBuilder()
                        .put("productPrice", price)
                        .put("refundBillDetailID", detailsID)
                        .put("refundBillID", refundBillID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 进行售后操作
     *
     * @param action           1. 客服审核 2. 司机提货 3. 仓库收货  4. 财务审核  5. 客服驳回  6. 取消  7. 财务关闭
     * @param refundBillIDs    退货单id，多个id 用逗号分隔
     * @param payType          自由退货客服审核必传的支付类型 1-货到付款 2- 账期支付
     * @param msg              客服审核或拒收消息
     * @param list             退货明细列表，司机收货和仓库收货时传入
     */
    public static void afterSalesAction(int action,
                                        String refundBillIDs,
                                        String payType,
                                        String msg,
                                        List<AfterSalesActionReq.ActionBean> list,
                                        SimpleObserver<MsgWrapper<AfterSalesActionResp>> observer) {
        AfterSalesActionReq req = new AfterSalesActionReq();
        req.setOrderAction(action);
        if (refundBillIDs.contains(",")) req.setRefundBillIDs(refundBillIDs);
        else req.setRefundBillID(refundBillIDs);
        req.setPayType(payType);
        if (action == 1) req.setCustomAuditNote(msg);
        else req.setReason(msg);
        req.setRefundBillDetailList(list);
        AfterSalesService.INSTANCE
                .afterSalesAction(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取协商历史
     *
     * @param refundBillID 退换货订单id
     * @param subBillID    订单id
     */
    public static void getNegotiationHistory(String refundBillID, String subBillID, SimpleObserver<NegotiationHistoryResp> observer) {
        AfterSalesService.INSTANCE
                .getNegotiationHistory(BaseMapReq.newBuilder()
                        .put("refundBillID", refundBillID)
                        .put("subBillID", subBillID).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /***
     * 生成投诉单
     */
    public static void generateComplain(AfterSalesBean afterSalesBean, SimpleObserver<GenerateCompainResp> observer) {
        List<CommitComplainProductBean> afterSalesDetailsBeans = new ArrayList<>();
        for (AfterSalesDetailsBean bean : afterSalesBean.getDetailList()) {
            CommitComplainProductBean commitComplainProductBean = new CommitComplainProductBean(bean);
            afterSalesDetailsBeans.add(commitComplainProductBean);
        }
        AfterSalesService.INSTANCE
                .generateComplain(BaseMapReq.newBuilder()
                        .put("sourceClient", UserConfig.crm() ? "6" : "1")
                        .put("target", "2")
                        .put("type", "1")
                        .put("reason", String.valueOf(afterSalesBean.getRefundReason()))
                        .put("source", "2")
                        .put("sourceBusiness", "2")
                        .put("actionType", "1")
                        .put("billID", afterSalesBean.getSubBillNo())
                        .put("complaintExplain", afterSalesBean.getRefundExplain())
                        .put("imgUrls", afterSalesBean.getRefundVoucher())
                        .put("products", JsonUtil.toJson(afterSalesDetailsBeans))
                        .put("purchaserContact", afterSalesBean.getReceiverMobile())
                        .put("purchaserID", afterSalesBean.getPurchaserID())
                        .put("purchaserName", afterSalesBean.getPurchaserName())
                        .put("purchaserShopID", afterSalesBean.getShopID())
                        .put("purchaserShopName", afterSalesBean.getShopName())
                        .put("supplyID", afterSalesBean.getGroupID())
                        .put("supplyName", afterSalesBean.getGroupName())
                        .put("supplyShopID", afterSalesBean.getSupplyShopID())
                        .put("supplyShopName", afterSalesBean.getSupplyShopName())
                        .put("refundBillNo", afterSalesBean.getRefundBillNo())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 验证是否可退换货
     *
     * @param refundBillType 退货单类型, 3 退货退款单 4 退押金
     * @param subBillID      订单ID
     */
    public static void afterSalesVerify(int refundBillType, String subBillID, SimpleObserver<AfterSalesVerifyResp> observer) {
        AfterSalesService.INSTANCE
                .afterSalesVerify(BaseMapReq.newBuilder()
                        .put("refundBillType", String.valueOf(refundBillType))
                        .put("subBillID", subBillID)
                        .put("flag", "2")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取可退明细
     *
     * @param subBillID      订单id
     * @param refundBillType 退货单类型
     */
    public static void getReturnableGoods(String subBillID, int refundBillType, SimpleObserver<SingleListResp<AfterSalesDetailsBean>> observer) {
        AfterSalesService.INSTANCE
                .getReturnableGoods(BaseMapReq.newBuilder()
                        .put("subBillID", subBillID)
                        .put("flag", "2")
                        .put("refundBillType", String.valueOf(refundBillType))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取退货退款原因
     */
    public static void getReasonList(SimpleObserver<SingleListResp<AfterSalesReasonBean>> observer) {
        AfterSalesService.INSTANCE
                .getReasonList(new BaseReq<>(new Object()))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 申请售后
     *
     * @param reapply 是否重新申请
     */
    public static void applyAfterSales(boolean reapply, AfterSalesApplyReq req, SimpleObserver<AfterSalesApplyResp> observer) {
        Observable<BaseResp<AfterSalesApplyResp>> observable;
        if (reapply)
            observable = AfterSalesService.INSTANCE.reapplyAfterSalesBill(new BaseReq<>(req));
        else observable = AfterSalesService.INSTANCE.applyAfterSalesBill(new BaseReq<>(req));
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取退款方式
     *
     * @param refundBillID 退货单id
     */
    public static void getRefundMethod(String refundBillID, SimpleObserver<SingleListResp<RefundMethodBean>> observer) {
        AfterSalesService.INSTANCE
                .getRefundMethod(BaseMapReq.newBuilder()
                        .put("refundBillID", refundBillID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
