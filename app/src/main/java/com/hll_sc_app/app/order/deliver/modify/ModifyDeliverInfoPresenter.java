package com.hll_sc_app.app.order.deliver.modify;

import android.support.annotation.NonNull;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class ModifyDeliverInfoPresenter implements IModifyDeliverInfoContract.IModifyDeliverInfoPresenter {
    private List<OrderDetailBean> mList;
    private String mSubBillID;
    private IModifyDeliverInfoContract.IModifyDeliverInfoView mView;

    private ModifyDeliverInfoPresenter(List<OrderDetailBean> list, String subBillID) {
        mList = list;
        mSubBillID = subBillID;
    }

    public static ModifyDeliverInfoPresenter newInstance(@NonNull List<OrderDetailBean> list, @NonNull String subBillID) {
        return new ModifyDeliverInfoPresenter(list, subBillID);
    }

    @Override
    public void modifyDeliverInfo() {
        Order.modifyDeliverInfo(mSubBillID, mList, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.modifySuccess();
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(IModifyDeliverInfoContract.IModifyDeliverInfoView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
