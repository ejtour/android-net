package com.hll_sc_app.app.order.deliver.modify;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public interface IModifyDeliverInfoContract {
    interface IModifyDeliverInfoView extends ILoadView {
        void modifySuccess();

        void modifyPrice();
    }

    interface IModifyDeliverInfoPresenter extends IPresenter<IModifyDeliverInfoView> {
        void modifyDeliverInfo();
    }
}
