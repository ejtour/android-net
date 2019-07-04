package com.hll_sc_app.app.order.transfer.inventory;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class InventoryCheckPresenter implements IInventoryCheckContract.IInventoryCheckPresenter {
    private IInventoryCheckContract.IInventoryCheckView mView;

    private InventoryCheckPresenter() {
    }

    public static InventoryCheckPresenter newInstance() {
        return new InventoryCheckPresenter();
    }

    @Override
    public void commitCheck(List<InventoryCheckReq.InventoryCheckBean> checkBeanList) {
        Order.commitInventoryCheck(checkBeanList, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.commitSuccess();
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(IInventoryCheckContract.IInventoryCheckView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
