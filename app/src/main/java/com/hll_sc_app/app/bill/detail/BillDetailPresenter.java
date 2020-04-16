package com.hll_sc_app.app.bill.detail;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Bill;

import java.util.Collections;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class BillDetailPresenter implements IBillDetailContract.IBillDetailPresenter {
    private String mID;
    private IBillDetailContract.IBillDetailView mView;

    public static BillDetailPresenter newInstance(String id) {
        BillDetailPresenter presenter = new BillDetailPresenter();
        presenter.mID = id;
        return presenter;
    }

    private BillDetailPresenter() {
    }

    @Override
    public void start() {
        Bill.getBillDetail(mID, new SimpleObserver<BillBean>(mView) {
            @Override
            public void onSuccess(BillBean bean) {
                mView.updateData(bean);
            }
        });
    }

    @Override
    public void register(IBillDetailContract.IBillDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void doAction() {
        Bill.billAction(Collections.singletonList(mID), getObserver());
    }

    @Override
    public void modifyAmount(String amount) {
        Bill.modifyAmount(mID, amount, getObserver());
    }

    private SimpleObserver<MsgWrapper<Object>> getObserver() {
        return new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.actionSuccess();
            }
        };
    }
}
