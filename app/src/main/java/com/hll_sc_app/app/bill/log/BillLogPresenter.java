package com.hll_sc_app.app.bill.log;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.bill.BillLogBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Bill;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */

class BillLogPresenter implements IBillLogContract.IBillLogPresenter {
    private IBillLogContract.IBillLogView mView;

    private BillLogPresenter() {
    }

    public static BillLogPresenter newInstance() {
        return new BillLogPresenter();
    }

    @Override
    public void start() {
        Bill.getBillLog(mView.getID(), new SimpleObserver<List<BillLogBean>>(mView) {
            @Override
            public void onSuccess(List<BillLogBean> billLogBeans) {
                mView.setData(billLogBeans);
            }
        });
    }

    @Override
    public void register(IBillLogContract.IBillLogView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
