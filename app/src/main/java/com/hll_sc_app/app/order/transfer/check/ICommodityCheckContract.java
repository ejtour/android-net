package com.hll_sc_app.app.order.transfer.check;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public interface ICommodityCheckContract {
    interface ICommodityCheckView extends ILoadView {
        void commitSuccess();
    }

    interface ICommodityCheckPresenter extends IPresenter<ICommodityCheckView> {
        void commitCheck(List<InventoryCheckReq.InventoryCheckBean> checkBeanList);
    }
}
