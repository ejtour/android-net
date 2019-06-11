package com.hll_sc_app.app.order.deliver;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public interface IDeliverInfoContract {
    interface IDeliverInfoView extends ILoadView {
        void updateShopList(List<DeliverShopResp> list);

        void updateInfoList(List<DeliverInfoResp> list);
    }

    interface IDeliverInfoPresenter extends IPresenter<IDeliverInfoView> {
        void requestShopList(String specID);
    }
}
