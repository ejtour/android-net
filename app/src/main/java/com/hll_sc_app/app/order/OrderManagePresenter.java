package com.hll_sc_app.app.order;

import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */
public class OrderManagePresenter implements IOrderManageContract.IOrderManagePresenter {
    private IOrderManageContract.IOrderManageView mView;
    private int mPageNum;

    private OrderManagePresenter() {
    }

    static OrderManagePresenter newInstance() {
        return new OrderManagePresenter();
    }

    @Override
    public void start() {
        if (mView.getOrderStatus() == OrderType.PENDING_DELIVER) {
            getDeliverNum(true);
        } else {
            mPageNum = 1;
            getOrderList(true);
        }
    }

    private void getDeliverNum(boolean showLoading) {
        Order.getDeliverNum(new SimpleObserver<DeliverNumResp>(mView, showLoading) {
            @Override
            public void onSuccess(DeliverNumResp resp) {
                if (!mView.updateDeliverHeader(resp.getPendDeliveryNum() == 0 ?
                        new ArrayList<>() : resp.getDeliverTypes())) {
                    mPageNum = 1;
                    getOrderList(showLoading);
                }
            }
        });
    }

    private void getOrderList(boolean showLoading) {
        Order.getOrderList(mPageNum,
                mView.getOrderStatus().getStatus(),
                mView.getOrderParam(),
                mView.getDeliverType(),
                new SimpleObserver<List<OrderResp>>(mView, showLoading) {
                    @Override
                    public void onSuccess(List<OrderResp> resps) {
                        if (mPageNum == 1) mView.updateListData(resps, false);
                        else mView.updateListData(resps, true);
                        if (!CommonUtils.isEmpty(resps)) mPageNum++;
                    }
                });
    }

    @Override
    public void register(IOrderManageContract.IOrderManageView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void refresh() {
        if (mView.getOrderStatus() == OrderType.PENDING_DELIVER) {
            getDeliverNum(false);
        } else {
            mPageNum = 1;
            getOrderList(false);
        }
    }

    @Override
    public void loadMore() {
        getOrderList(false);
    }

    @Override
    public void getOrderDetails(String subBillId) {
        Order.getOrderDetails(subBillId, new SimpleObserver<OrderResp>(mView) {
            @Override
            public void onSuccess(OrderResp resp) {
                EventBus.getDefault().post(new OrderEvent(OrderEvent.UPDATE_ITEM, resp));
            }
        });
    }

    @Override
    public void receiveOrder(String subBillIds) {
        Order.modifyOrderStatus(1, subBillIds, 0,
                null, null, null,
                new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.showToast("成功接单");
                        mView.statusChanged();
                    }
                });
    }

    @Override
    public void deliver(String subBillIds, String expressName, String expressNo) {
        Order.modifyOrderStatus(2, subBillIds, 0,
                null, expressName, expressNo,
                new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.showToast("成功发货");
                        mView.statusChanged();
                    }
                });
    }

    @Override
    public void exportAssemblyOrder(List<String> subBillIds, String email) {
        Order.exportAssembly(mView.getOrderParam(), mView.getOrderStatus().getStatus(), mView.getDeliverType(), subBillIds, email, Utils.getExportObserver(mView));
    }

    @Override
    public void exportDeliveryOrder(List<String> subBillIds, String email) {
        Order.exportDelivery(mView.getOrderParam(), subBillIds, email, Utils.getExportObserver(mView));
    }

    @Override
    public void exportSpecialOrder(int type, String email) {
        Order.exportSpecial(mView.getOrderParam(), mView.getOrderStatus().getStatus(), type, email, Utils.getExportObserver(mView));
    }

    @Override
    public void exportNormalOrder(int type, String email, List<String> billNoLists) {
        Order.exportNormal(mView.getOrderParam(), mView.getOrderStatus().getStatus(), type, mView.getDeliverType(),
                null, email, billNoLists, Utils.getExportObserver(mView));
    }

    @Override
    public void getExpressCompanyList(String groupID, String shopID) {
        Order.getExpressCompanyList(groupID, shopID, new SimpleObserver<ExpressResp>(mView) {
            @Override
            public void onSuccess(ExpressResp expressResp) {
                mView.showExpressInfoDialog(expressResp.getDeliveryCompanyList());
            }
        });
    }
}
