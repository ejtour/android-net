package com.hll_sc_app.app.inspection.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.impl.IPurchaserContract;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public interface IInspectionListContact {
    interface IInspectionListView extends ILoadView, IPurchaserContract.IPurchaserView {
        void showList(List<InspectionBean> list, boolean append);
    }

    interface IInspectionListPresenter extends IPresenter<IInspectionListView>, IPurchaserContract.IPurchaserPresenter {
        void reload();

        void loadMore();

        void refresh();
    }
}
