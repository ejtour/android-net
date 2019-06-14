package com.hll_sc_app.app.order;

import android.text.TextUtils;

import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Constants;

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
            refreshList();
        }
    }

    @Override
    public void refreshList() {
        mPageNum = 1;
        getOrderList(true);
    }

    private void getDeliverNum(boolean showLoading) {
        Order.getDeliverNum(new SimpleObserver<DeliverNumResp>(mView, showLoading) {
            @Override
            public void onSuccess(DeliverNumResp resp) {
                mView.updateDeliverHeader(resp.getDeliverTypes());
                mPageNum = 1;
                getOrderList(showLoading);
            }
        });
    }

    private void getOrderList(boolean showLoading) {
        OrderParam param = mView.getOrderParam();
        if (mView.getOrderStatus() != OrderType.PENDING_TRANSFER) { // 如果不是待转单
            Order.getOrderList(mPageNum,
                    param.getFlag(),
                    mView.getOrderStatus().getType(),
                    param.getSearchWords(),
                    param.getFormatCreateStart(Constants.FORMAT_YYYY_MM_DD),
                    param.getFormatCreateEnd(Constants.FORMAT_YYYY_MM_DD),
                    param.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_HH),
                    param.getFormatExecuteEnd(Constants.FORMAT_YYYY_MM_DD_HH),
                    param.getFormatSignStart(Constants.FORMAT_YYYY_MM_DD_HH),
                    param.getFormatSignEnd(Constants.FORMAT_YYYY_MM_DD_HH),
                    mView.getDeliverType(),
                    new SimpleObserver<List<OrderResp>>(mView, showLoading) {
                        @Override
                        public void onSuccess(List<OrderResp> orderResps) {
                            if (mPageNum == 1) {
                                mView.refreshListData(orderResps);
                            } else {
                                mView.appendListData(orderResps);
                            }
                            if (!CommonUtils.isEmpty(orderResps)) {
                                mPageNum++;
                            }
                        }
                    });
        }
    }

    @Override
    public void register(IOrderManageContract.IOrderManageView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void refresh() {
        if (mView.getOrderStatus() == OrderType.PENDING_DELIVER && mView.getDeliverType() == null) {
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
        Order.exportAssembly(subBillIds, email, getExportObserver());
    }

    @Override
    public void exportDeliveryOrder(List<String> subBillIds, String email) {
        Order.exportDelivery(subBillIds, email, getExportObserver());
    }

    @Override
    public void exportSpecialOrder(int type, String email) {
        Order.exportSpecial(mView.getOrderParam(), mView.getOrderStatus().getType(), type, email, getExportObserver());
    }

    @Override
    public void exportNormalOrder(int type, String email) {
        Order.exportNormal(mView.getOrderParam(), mView.getOrderStatus().getType(), type, email, getExportObserver());
    }

    @Override
    public void getExpressCompanyList(String groupID, String shopID) {
        Order.getExpressCompanyList(groupID, shopID, new SimpleObserver<ExpressResp>(mView) {
            @Override
            public void onSuccess(ExpressResp expressResp) {
                mView.showExpressCompanyList(expressResp.getDeliveryCompanyList(), null);
            }
        });
    }

    private SimpleObserver<ExportResp> getExportObserver() {
        return new SimpleObserver<ExportResp>(mView) {
            @Override
            public void onSuccess(ExportResp exportResp) {
                if (!TextUtils.isEmpty(exportResp.getEmail()))
                    mView.exportSuccess(exportResp.getEmail());
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }

            @Override
            public void onFailure(UseCaseException e) {
                if ("00120112037".equals(e.getCode())) mView.bindEmail();
                else if ("00120112038".equals(e.getCode())) mView.exportFailure("当前没有可导出的数据");
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }
        };
    }
}
