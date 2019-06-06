package com.hll_sc_app.rest;

import com.hll_sc_app.api.OrderService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.OrderResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

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
     * @param createTimeStart  下单开始时间
     * @param createTimeEnd    下单结束时间
     * @param executeDateStart 到货开始时间
     * @param executeDateEnd   到货结束时间
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
                        .put("deliverType", deliverType)
                        .put("searchWords", searchWords)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 请求订单明细
     *
     * @param subBillID 订单 id
     */
    public static void getOrderDetails(int subBillID, SimpleObserver<OrderResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) {
            return;
        }
        OrderService.INSTANCE
                .getOrderDetails(BaseMapReq
                        .newBuilder()
                        .put("subBillID", String.valueOf(subBillID))
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
}
