package com.hll_sc_app.app.bill.list;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.impl.IPurchaserContract;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public interface IBillListContract {
    interface IBillListView extends IExportView, IPurchaserContract.IPurchaserView {
        void setBillList(List<BillBean> list, boolean isMore);

        void actionSuccess();
    }

    interface IBillListPresenter extends IPresenter<IBillListView>, IPurchaserContract.IPurchaserPresenter {
        void export(String email, int sign);

        void doAction(List<Integer> ids);

        void refresh();

        void loadMore();
    }
}
