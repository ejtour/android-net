package com.hll_sc_app.app.inspection.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.inspection.InspectionBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public interface IInspectionListContract {
    interface IInspectionListView extends ILoadView {
        void showList(List<InspectionBean> list, boolean append);

        void cachePurchaserList(List<PurchaserBean> list);
    }

    interface IInspectionListPresenter extends IPresenter<IInspectionListView> {
        void reload();

        void loadMore();

        void refresh();

        void getPurchaserList();
    }
}
