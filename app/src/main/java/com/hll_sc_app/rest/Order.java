package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.api.OrderService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.export.OrderExportReq;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.bean.order.deliver.ModifyDeliverInfoReq;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.bean.order.search.OrderSearchResp;
import com.hll_sc_app.bean.order.settle.CashierResp;
import com.hll_sc_app.bean.order.settle.PayWaysResp;
import com.hll_sc_app.bean.order.settle.SettlementResp;
import com.hll_sc_app.bean.order.shop.OrderShopResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
import com.hll_sc_app.bean.order.transfer.OrderResultResp;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.bean.order.transfer.TransferResp;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class Order {

    /**
     * 获取订单列表
     *
     * @param pageNum          页码
     * @param subBillStatus    订单状态
     * @param shopID           搜索门店ID
     * @param searchWords      搜索词
     * @param createTimeStart  下单开始时间 yyyyMMdd
     * @param createTimeEnd    下单结束时间 yyyyMMdd
     * @param signTimeStart    签收开始时间 yyyyMMddHH
     * @param signTimeEnd      签收结束时间 yyyyMMddHH
     * @param executeDateStart 到货开始时间 yyyyMMddHH
     * @param executeDateEnd   到货结束时间 yyyyMMddHH
     * @param deliverType      发货类型 1 自有物流配送 2 自提 3 第三方配送
     */
    public static void getOrderList(int pageNum,
                                    int subBillStatus,
                                    String searchWords,
                                    String shopID,
                                    String createTimeStart,
                                    String createTimeEnd,
                                    String executeDateStart,
                                    String executeDateEnd,
                                    String signTimeStart,
                                    String signTimeEnd,
                                    String deliverType,
                                    SimpleObserver<List<OrderResp>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            return;
        }
        OrderService.INSTANCE
                .getOrderList(BaseMapReq
                        .newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("flag", "0")
                        .put("subBillStatus", String.valueOf(subBillStatus))
                        .put("subBillCreateTimeStart", createTimeStart)
                        .put("subBillCreateTimeEnd", createTimeEnd)
                        .put("subBillExecuteDateStart", executeDateStart)
                        .put("subBillExecuteDateEnd", executeDateEnd)
                        .put("subBillSignTimeStart", signTimeStart)
                        .put("subBillSignTimeEnd", signTimeEnd)
                        .put("deliverType", deliverType)
                        .put("searchWords", searchWords)
                        .put("shopID", shopID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取待转单列表
     *
     * @param pageNum         页数
     * @param createTimeStart 开始时间
     * @param createTimeEnd   结束时间
     * @param searchWords     搜索词
     * @param shopID          搜索门店ID
     */
    public static void getPendingTransferList(int pageNum,
                                              String createTimeStart,
                                              String createTimeEnd,
                                              String searchWords,
                                              String shopID,
                                              SimpleObserver<TransferResp> observer) {
        OrderService.INSTANCE
                .getPendingTransferList(BaseMapReq.newBuilder()
                        .put("plateSupplierID", UserConfig.getGroupID())
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("mutiStatus", "1,3")
                        .put("searchWords", searchWords)
                        .put("shopID", shopID)
                        .put("beginDate", createTimeStart)
                        .put("endDate", createTimeEnd).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 请求订单明细
     *
     * @param subBillID 订单 id
     */
    public static void getOrderDetails(String subBillID, SimpleObserver<OrderResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            return;
        }
        OrderService.INSTANCE
                .getOrderDetails(BaseMapReq
                        .newBuilder()
                        .put("subBillID", subBillID)
                        .put("roleTypes", user.getAuthType())
                        .put("curRole", user.getAuthType())
                        .put("groupID", user.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 修改订单状态
     *
     * @param actionType   操作类型 1-接单，2-确认发货，3-放弃，4-确认收货
     * @param subBillIds   订单ID (多个订单 ID 以逗号隔开)
     * @param canceler     订单取消方 1-采购商 2-供应商 3-客服 4-销售
     * @param cancelReason 取消原因
     * @param expressName  物流公司
     * @param expressNo    物流单号
     */
    public static void modifyOrderStatus(int actionType,
                                         String subBillIds,
                                         int canceler,
                                         String cancelReason,
                                         String expressName,
                                         String expressNo,
                                         SimpleObserver<Object> observer) {
        OrderService.INSTANCE
                .modifyOrderStatus(BaseMapReq.newBuilder()
                        .put("actionType", String.valueOf(actionType))
                        .put("subBillIds", subBillIds)
                        .put("canceler", canceler == 0 ? "" : String.valueOf(canceler))
                        .put("cancelReason", cancelReason)
                        .put("expressName", expressName)
                        .put("expressNo", expressNo).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /**
     * 请求搜索
     *
     * @param searchWords 搜索词
     */
    public static void requestSearch(String searchWords, SimpleObserver<OrderSearchResp> observer) {
        OrderService.INSTANCE
                .requestSearch(BaseMapReq.newBuilder()
                        .put("searchWords", searchWords)
                        .put("source", "0")
                        .put("shopMallID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }

    /**
     * 获取待发货商品信息
     */
    public static void getDeliverInfo(SimpleObserver<List<DeliverInfoResp>> observer) {
        OrderService.INSTANCE
                .getOrderDeliverInfo(BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取待发货商品总量，包含发货类型数据
     */
    public static void getDeliverNum(SimpleObserver<DeliverNumResp> observer) {
        OrderService.INSTANCE
                .getOrderDeliverNum(BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取待发货商品门店信息
     *
     * @param productSpecID 商品规格 ID
     */
    public static void getDeliverShop(String productSpecID, SimpleObserver<List<DeliverShopResp>> observer) {
        OrderService.INSTANCE
                .getOrderDeliverShop(BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID())
                        .put("productSpecID", productSpecID).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 修改发货信息
     *
     * @param subBillID 订单 id
     * @param beans     商品明细列表
     */
    public static void modifyDeliverInfo(String subBillID, List<OrderDetailBean> beans, SimpleObserver<Object> observer) {
        List<ModifyDeliverInfoReq.ProductBean> list = new ArrayList<>();
        for (OrderDetailBean bean : beans) {
            list.add(ModifyDeliverInfoReq.ProductBean.convertFromOrderDetails(bean));
        }
        ModifyDeliverInfoReq req = new ModifyDeliverInfoReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setBillDeliveryList(list);
        req.setSubBillID(subBillID);
        OrderService.INSTANCE
                .modifyDeliverInfo(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出明细出库单
     *
     * @param subBillIds 订单列表
     * @param email      邮件地址
     */
    public static void exportDelivery(List<String> subBillIds, String email, SimpleObserver<ExportResp> observer) {
        OrderExportReq req = new OrderExportReq(subBillIds, UserConfig.getGroupID(), email);
        OrderService.INSTANCE
                .exportDelivery(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出配货单
     *
     * @param subBillIds 订单列表
     * @param email      邮件地址
     */
    public static void exportAssembly(List<String> subBillIds, String email, SimpleObserver<ExportResp> observer) {
        OrderExportReq req = new OrderExportReq(subBillIds, UserConfig.getGroupID(), email);
        OrderService.INSTANCE
                .exportAssembly(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出验货单、分类出库单
     *
     * @param param         订单参数
     * @param subBillStatus 订单状态
     * @param type          0-导出明细验货单 1-导出分类出库单 2-导出分类验货单
     * @param email         邮件地址
     */
    public static void exportSpecial(OrderParam param, int subBillStatus, int type, String email, SimpleObserver<ExportResp> observer) {
        OrderService.INSTANCE
                .exportSpecial(buildSpecialExportReq(param, subBillStatus, type, email)
                        .put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出订单、订单详情
     *
     * @param param         订单参数
     * @param subBillStatus 订单状态
     * @param type          0-订单导出 1-订单明细导出 必传
     * @param shopID        crm订单导出用到的shopID
     * @param email         邮件地址
     */
    public static void exportNormal(OrderParam param, int subBillStatus, int type, String shopID, String email, SimpleObserver<ExportResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OrderService.INSTANCE
                .exportNormal(buildSpecialExportReq(param, subBillStatus, type, email)
                        .put("flag", "1".equals(user.getAuthType()) ? "2" : "0")
                        .put("shopID", shopID)
                        .put("groupIDs", user.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    private static BaseMapReq.Builder buildSpecialExportReq(OrderParam param, int subBillStatus, int type, String email) {
        return BaseMapReq.newBuilder()
                .put("email", email)
                .put("isBindEmail", TextUtils.isEmpty(email) ? "" : "1")
                .put("searchWords", param.getSearchWords())
                .put("subBillCreateTimeEnd", param.getFormatCreateEnd(Constants.UNSIGNED_YYYY_MM_DD))
                .put("subBillCreateTimeStart", param.getFormatCreateStart(Constants.UNSIGNED_YYYY_MM_DD))
                .put("subBillExecuteDateEnd", param.getFormatExecuteEnd(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillExecuteDateStart", param.getFormatExecuteStart(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillSignTimeEnd", param.getFormatSignEnd(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillSignTimeStart", param.getFormatSignStart(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillStatus", subBillStatus == 0 ? "" : String.valueOf(subBillStatus))
                .put("type", String.valueOf(type));
    }

    /**
     * 获取物流公司列表
     *
     * @param groupID 供应商集团ID
     * @param shopID  门店ID
     */
    public static void getExpressCompanyList(String groupID, String shopID, SimpleObserver<ExpressResp> observer) {
        OrderService.INSTANCE
                .getExpressCompanyList(BaseMapReq.newBuilder()
                        .put("groupID", groupID)
                        .put("shopID", shopID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 验货或拒收订单
     */
    public static void inspectionOrder(OrderInspectionReq req, SimpleObserver<OrderInspectionResp> observer) {
        OrderService.INSTANCE
                .inspectionOrder(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取支付方式列表
     *
     * @param payType 支付类型
     */
    public static void getPayWays(int payType, SimpleObserver<PayWaysResp> observer) {
        OrderService.INSTANCE
                .getPayWays(BaseMapReq.newBuilder()
                        .put("payType", String.valueOf(payType))
                        .put("source", "2")
                        .put("supplyID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取收银台
     *
     * @param payType   支付方式 1-银行卡 2-余额 3-微信扫码 4-支付宝扫码 5-微信公众号支付 6-微信app支付
     * @param subBillID 订单编号
     */
    public static void getCashier(String payType, String subBillID, SimpleObserver<CashierResp> observer) {
        OrderService.INSTANCE
                .getCashier(BaseMapReq.newBuilder()
                        .put("flag", "2") // 支付标识 1-合并支付 2-单笔支付
                        .put("payType", payType)
                        .put("source", "CODPay")
                        .put("subBillIDs", subBillID)
                        .put("terminalIp", UIUtils.getIpAddressString()) // 终端IP 必传
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 验货支付
     *
     * @param paymentWay 付款方式：1-微信付款、2-支付宝付款、3-银联支付、4-现金、5-支票 6-快捷支付、7-余额支付
     * @param subBillID  订单编号
     */
    public static void inspectionPay(String paymentWay, String subBillID, SimpleObserver<Object> observer) {
        OrderService.INSTANCE
                .inspectionPay(BaseMapReq.newBuilder()
                        .put("paymentWay", paymentWay)
                        .put("subBillIDs", subBillID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取支付结果
     *
     * @param payOrderNo 支付订单号
     */
    public static void getSettlementStatus(String payOrderNo, SimpleObserver<SettlementResp> observer) {
        OrderService.INSTANCE
                .getSettlementStatus(BaseMapReq.newBuilder()
                        .put("PayOrderNo", payOrderNo).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 商城下单
     *
     * @param ids 选中的订单id列表
     */
    public static void mallOrder(List<String> ids, SimpleObserver<OrderResultResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionBy", user.getEmployeeName())
                .put("plateSupplierID", user.getGroupID())
                .put("id", ids.size() == 1 ? ids.get(0) : null)
                .put("ids", ids.size() > 1 ? TextUtils.join(",", ids) : null)
                .create();
        Observable<BaseResp<OrderResultResp>> observable = ids.size() > 1 ? OrderService.INSTANCE
                .batchMallOrder(req) : OrderService.INSTANCE
                .mallOrder(req);
        observable
                .compose(ApiScheduler.getObservableScheduler())
                .map(orderResultRespBaseResp -> {
                    if (!orderResultRespBaseResp.isSuccess()) {
                        throw new UseCaseException(orderResultRespBaseResp.getCode(), orderResultRespBaseResp.getMessage());
                    }
                    if (!TextUtils.isEmpty(orderResultRespBaseResp.getMessage())) {
                        ToastUtils.showShort(MyApplication.getInstance(), orderResultRespBaseResp.getMessage());
                    }
                    return orderResultRespBaseResp.getData();
                })
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取转单明细
     *
     * @param id 订单id
     */
    public static void getTransferDetail(String id, SimpleObserver<TransferBean> observer) {
        OrderService.INSTANCE.getTransferDetail(
                BaseMapReq.newBuilder()
                        .put("id", id).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 取消转单
     *
     * @param actionBy     操作人
     * @param ids          订单 id
     * @param billSource   订单来源：1-哗啦啦供应链 2-天财供应链
     * @param cancelReason 取消原因
     */
    public static void cancelTransfer(String actionBy, String ids, int billSource, String cancelReason, SimpleObserver<Object> observer) {
        OrderService.INSTANCE.cancelTransfer(BaseMapReq.newBuilder()
                .put("actionBy", actionBy)
                .put("ids", ids)
                .put("billSource", String.valueOf(billSource))
                .put("cancelReason", cancelReason)
                .put("flag", "2")
                .put("plateSupplierID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 提交库存检查
     *
     * @param beans 库存提交列表
     */
    public static void commitInventoryCheck(List<InventoryCheckReq.InventoryCheckBean> beans, SimpleObserver<MsgWrapper<Object>> observer) {
        OrderService.INSTANCE
                .commitInventoryCheck(new BaseReq<>(new InventoryCheckReq(beans)))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 标记指定转单明细不关联
     *
     * @param id 明细id
     */
    public static void tagDoNotRelevance(String id, SimpleObserver<Object> observer) {
        OrderService.INSTANCE
                .tagDoNotRelevance(BaseMapReq.newBuilder()
                        .put("id", id).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取下单门店列表数据
     *
     * @param billStatus       订单状态 0-未下单 1-已下单
     * @param actionType       1-今日订单 2-全部订单
     * @param pageNum          页码
     * @param searchWords      搜索词
     * @param createTimeStart  下单开始时间 yyyyMMdd
     * @param createTimeEnd    下单结束时间 yyyyMMdd
     * @param signTimeStart    签收开始时间 yyyyMMddHH
     * @param signTimeEnd      签收结束时间 yyyyMMddHH
     * @param executeDateStart 到货开始时间 yyyyMMddHH
     * @param executeDateEnd   到货结束时间 yyyyMMddHH
     */
    public static void queryOrderShopList(int billStatus,
                                          String actionType,
                                          int pageNum,
                                          String searchWords,
                                          String createTimeStart,
                                          String createTimeEnd,
                                          String executeDateStart,
                                          String executeDateEnd,
                                          String signTimeStart,
                                          String signTimeEnd,
                                          SimpleObserver<OrderShopResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OrderService.INSTANCE
                .queryOrderShopList(BaseMapReq.newBuilder()
                        .put("billStatus", String.valueOf(billStatus))
                        .put("actionType", actionType)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("salesmanID", user.getEmployeeID())
                        .put("groupID", user.getGroupID())
                        .put("searchWords", searchWords)
                        .put("subBillCreateTimeStart", createTimeStart)
                        .put("subBillCreateTimeEnd", createTimeEnd)
                        .put("subBillExecuteDateStart", executeDateStart)
                        .put("subBillExecuteDateEnd", executeDateEnd)
                        .put("subBillSignTimeStart", signTimeStart)
                        .put("subBillSignTimeEnd", signTimeEnd)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 销售查询订单列表
     *
     * @param pageNum          页码
     * @param shopID           门店id
     * @param createTimeStart  下单开始时间 yyyyMMdd
     * @param createTimeEnd    下单结束时间 yyyyMMdd
     * @param signTimeStart    签收开始时间 yyyyMMddHH
     * @param signTimeEnd      签收结束时间 yyyyMMddHH
     * @param executeDateStart 到货开始时间 yyyyMMddHH
     * @param executeDateEnd   到货结束时间 yyyyMMddHH
     */
    public static void crmQueryOrderList(int pageNum, String shopID,
                                         int subBillStatus,
                                         String createTimeStart,
                                         String createTimeEnd,
                                         String executeDateStart,
                                         String executeDateEnd,
                                         String signTimeStart,
                                         String signTimeEnd,
                                         SimpleObserver<SingleListResp<OrderResp>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        OrderService.INSTANCE
                .crmQueryOrderList(BaseMapReq.newBuilder()
                        .put("curRole", user.getAuthType())
                        .put("shopID", shopID)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("salesmanID", user.getEmployeeID())
                        .put("groupID", user.getGroupID())
                        .put("subBillStatus", subBillStatus == 0 ? "" : String.valueOf(subBillStatus))
                        .put("roleTypes", user.getAuthType())
                        .put("subBillCreateTimeStart", createTimeStart)
                        .put("subBillCreateTimeEnd", createTimeEnd)
                        .put("subBillExecuteDateStart", executeDateStart)
                        .put("subBillExecuteDateEnd", executeDateEnd)
                        .put("subBillSignTimeStart", signTimeStart)
                        .put("subBillSignTimeEnd", signTimeEnd)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询订单日志
     *
     * @param billID 订单id
     */
    public static void queryOrderLog(String billID, SimpleObserver<SingleListResp<OrderTraceBean>> observer) {
        if (TextUtils.isEmpty(UserConfig.getSalesmanID())) return;
        OrderService.INSTANCE
                .queryOrderLog(BaseMapReq.newBuilder()
                        .put("billID", billID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);

    }
}
