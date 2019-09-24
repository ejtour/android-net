package com.hll_sc_app.app.order.place.commit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.place.OrderCommitBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */

public interface IPlaceOrderCommitContract {
    interface IPlaceOrderCommitView extends ILoadView {
        void handleCommitResp(OrderCommitBean bean);
    }

    interface IPlaceOrderCommitPresenter extends IPresenter<IPlaceOrderCommitView> {

    }
}
