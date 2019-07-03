package com.hll_sc_app.app.order.transfer.details;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.TransferBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public interface ITransferDetailContract {
    interface ITransferDetailView extends ILoadView{
        void handleStatusChanged();

        void updateOrderData(TransferBean bean);
    }

    interface ITransferDetailPresenter extends IPresenter<ITransferDetailView>{
        void orderCancel(String cancelReason,int billSource);

        void mallOrder();
    }
}
