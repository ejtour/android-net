package com.hll_sc_app.app.order.transfer.check;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class CommodityCheckPresenter implements ICommodityCheckContract.ICommodityCheckPresenter {
    private ICommodityCheckContract.ICommodityCheckView mView;

    private CommodityCheckPresenter() {
    }

    public static CommodityCheckPresenter newInstance() {
        return new CommodityCheckPresenter();
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
    public void register(ICommodityCheckContract.ICommodityCheckView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
