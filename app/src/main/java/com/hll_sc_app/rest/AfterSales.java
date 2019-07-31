package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.AfterSalesService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aftersales.AfterSalesActionReq;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.aftersales.CommitComplainProductBean;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;
import com.hll_sc_app.bean.aftersales.PurchaserListResp;
import com.hll_sc_app.bean.export.ExportResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

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
                null, null, refundBillID,
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
     * @param sourceType       0：全部 1：自由退货 2：快速退货
     * @param pageNum          页码
     */
    public static void requestAfterSalesList(
            String startTime, String endTime,
            Integer refundBillStatus,
            String purchaserShopID,
            String purchaserID,
            String refundBillID,
            int sourceType,
            int pageNum,
            SimpleObserver<List<AfterSalesBean>> observer) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("flag", "1")
                .put("groupID", UserConfig.getGroupID());
        if (refundBillID != null)
            builder.put("refundBillID", refundBillID)
                    .put("sign", "2");
        else
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
     * @param action           1. 客服审核 2. 司机提货 3. 仓库收货  4. 财务审核  5. 客服驳回、司机取消 6. 采购商取消
     * @param refundBillIDs    退货单id，多个id 用逗号分隔
     * @param refundBillStatus 订单状态 1-待处理 2-已处理待收货 3-退货发货确认 4-供应商已收货 5-已退款 6-驳回 7-关闭 8-退款受理
     * @param refundBillType   订单类型 1 退款单 2 验货差异处理单 3 退货退款单
     * @param payType          自由退货客服审核必传的支付类型 1-货到付款 2- 账期支付
     * @param msg              客服审核或拒收消息
     * @param list             退货明细列表，司机收货和仓库收货时传入
     */
    public static void afterSalesAction(int action,
                                        String refundBillIDs,
                                        int refundBillStatus,
                                        int refundBillType,
                                        String payType,
                                        String msg,
                                        List<AfterSalesActionReq.ActionBean> list,
                                        SimpleObserver<MsgWrapper<Object>> observer) {
        AfterSalesActionReq req = new AfterSalesActionReq();
        req.setOrderAction(action);
        if (refundBillIDs.contains(",")) req.setRefundBillIDs(refundBillIDs);
        else req.setRefundBillID(refundBillIDs);
        req.setRefundBillStatus(refundBillStatus);
        req.setRefundBillType(refundBillType);
        req.setPayType(payType);
        if (action == 1) req.setCustomAuditNote(msg);
        else req.setRefuseReason(msg);
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
                        .put("sourceClient", "1")//todo crm的话 这块要改为6
                        .put("target", "2")
//                        .put("type", "1")
                        .put("reason", String.valueOf(afterSalesBean.getRefundReason()))
                        .put("source", "2")
                        .put("sourceBusiness", "2")
                        .put("actionType", "1")
                        .put("billID", afterSalesBean.getSubBillID())
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

}
