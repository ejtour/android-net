package com.hll_sc_app.app.crm.order.list;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/6
 */

public interface ICrmOrderListContract {
    interface ICrmOrderListView extends IExportView {
        void setData(List<OrderResp> list, boolean append);

        String getShopID();

        int getBillStatus();
    }

    interface ICrmOrderListPresenter extends IPresenter<ICrmOrderListView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
