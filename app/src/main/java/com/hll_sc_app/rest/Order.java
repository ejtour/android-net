package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.OrderService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.export.OrderExportReq;
import com.hll_sc_app.bean.order.OrderParam;
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
import com.hll_sc_app.utils.Constants;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

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
     * @param flag             排序，0下单排序，1到货排序
     * @param subBillStatus    订单状态
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
                                    int flag,
                                    int subBillStatus,
                                    String searchWords,
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
                        .put("curRole", user.getRoleID())
                        .put("groupID", user.getGroupID())
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("flag", String.valueOf(flag))
                        .put("subBillStatus", String.valueOf(subBillStatus))
                        .put("subBillCreateTimeStart", createTimeStart)
                        .put("subBillCreateTimeEnd", createTimeEnd)
                        .put("subBillExecuteDateStart", executeDateStart)
                        .put("subBillExecuteDateEnd", executeDateEnd)
                        .put("subBillSignTimeStart", signTimeStart)
                        .put("subBillSignTimeEnd", signTimeEnd)
                        .put("deliverType", deliverType)
                        .put("searchWords", searchWords)
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
     */
    public static void getPendingTransferList(int pageNum,
                                              String createTimeStart,
                                              String createTimeEnd,
                                              String searchWords,
                                              SimpleObserver<List<OrderResp>> observer) {
        OrderService.INSTANCE
                .getPendingTransferList(BaseMapReq.newBuilder()
                        .put("plateSupplierID", UserConfig.getGroupID())
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("searchWords", searchWords)
                        .put("beginExcuteDate", createTimeStart)
                        .put("endExecuteDate", createTimeEnd).create())
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
                        .put("groupID", user.getGroupID())
                        .put("curRole", user.getRoleID())
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
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            throw new IllegalStateException("未登录");
        }
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
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            throw new IllegalStateException("未登录");
        }
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
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            throw new IllegalStateException("未登录");
        }
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
     * @param email         邮件地址
     */
    public static void exportNormal(OrderParam param, int subBillStatus, int type, String email, SimpleObserver<ExportResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            throw new IllegalStateException("未登录");
        }
        OrderService.INSTANCE
                .exportNormal(buildSpecialExportReq(param, subBillStatus, type, email)
                        .put("flag", "0")
                        .put("groupIDs", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    private static BaseMapReq.Builder buildSpecialExportReq(OrderParam param, int subBillStatus, int type, String email) {
        return BaseMapReq.newBuilder()
                .put("email", email)
                .put("isBindEmail", TextUtils.isEmpty(email) ? "" : "1")
                .put("searchWords", param.getSearchWords())
                .put("subBillCreateTimeEnd", param.getFormatCreateEnd(Constants.FORMAT_YYYY_MM_DD))
                .put("subBillCreateTimeStart", param.getFormatCreateStart(Constants.FORMAT_YYYY_MM_DD))
                .put("subBillExecuteDateEnd", param.getFormatExecuteEnd(Constants.FORMAT_YYYY_MM_DD_HH))
                .put("subBillExecuteDateStart", param.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_HH))
                .put("subBillSignTimeEnd", param.getFormatSignEnd(Constants.FORMAT_YYYY_MM_DD_HH))
                .put("subBillSignTimeStart", param.getFormatSignStart(Constants.FORMAT_YYYY_MM_DD_HH))
                .put("subBillStatus", String.valueOf(subBillStatus))
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
}
