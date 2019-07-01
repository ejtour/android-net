package com.hll_sc_app.app.order.settle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.settle.CashierResp;
import com.hll_sc_app.bean.order.settle.PayWaysResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public class OrderSettlementPresenter implements IOrderSettlementContract.IOrderSettlementPresenter {
    private IOrderSettlementContract.IOrderSettlementView mView;
    private String mSubBillID;

    private OrderSettlementPresenter() {
    }

    public static OrderSettlementPresenter newInstance(String subBillID) {
        OrderSettlementPresenter presenter = new OrderSettlementPresenter();
        presenter.mSubBillID = subBillID;
        return presenter;
    }

    @Override
    public void start() {

    }

    @Override
    public void register(IOrderSettlementContract.IOrderSettlementView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void getPayWays(int payType) {
        Order.getPayWays(payType, new SimpleObserver<PayWaysResp>(mView) {
            @Override
            public void onSuccess(PayWaysResp payWaysResp) {
                if (CommonUtils.isEmpty(payWaysResp.getRecords())) {
                    mView.showToast("暂无可用的支付方式");
                    mView.closeActivity();
                    return;
                }
                mView.showPayWays(payWaysResp.getRecords());
            }
        });
    }

    @Override
    public void inspectionPay(String paymentWay) {
        Order.inspectionPay(paymentWay, mSubBillID, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.settleSuccess();
            }
        });
    }

    @Override
    public void getCashier(String payType) {
        Order.getCashier(payType, mSubBillID, new SimpleObserver<CashierResp>(mView) {
            @Override
            public void onSuccess(CashierResp cashierResp) {
                mView.showQRCode(cashierResp);
            }
        });
    }
}
