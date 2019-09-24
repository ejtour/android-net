package com.hll_sc_app.app.order.place.commit;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.place.OrderCommitBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */

public class PlaceOrderCommitPresenter implements IPlaceOrderCommitContract.IPlaceOrderCommitPresenter {
    private IPlaceOrderCommitContract.IPlaceOrderCommitView mView;
    private String mID;

    private PlaceOrderCommitPresenter(String ID) {
        mID = ID;
    }

    public static PlaceOrderCommitPresenter newInstance(String id) {
        return new PlaceOrderCommitPresenter(id);
    }

    @Override
    public void start() {
        Order.queryCommitResult(mID, new SimpleObserver<List<OrderCommitBean>>(mView) {
            @Override
            public void onSuccess(List<OrderCommitBean> orderCommitBeans) {
                if (!CommonUtils.isEmpty(orderCommitBeans)) {
                    mView.handleCommitResp(orderCommitBeans.get(0));
                }
            }
        });
    }

    @Override
    public void register(IPlaceOrderCommitContract.IPlaceOrderCommitView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
