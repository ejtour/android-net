package com.hll_sc_app.app.aftersales.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/10
 */

public interface IAfterSalesListContract {
    interface IAfterSalesListView extends ILoadView {
        void setData(List<AfterSalesBean> list, boolean append);
    }

    interface IAfterSalesListPresenter extends IPresenter<IAfterSalesListView> {
        void refresh();

        void loadMore();
    }
}
