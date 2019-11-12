package com.hll_sc_app.app.goodsdemand.special.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public interface ISpecialDemandListContract {
    interface ISpecialDemandListView extends ILoadView {
        void setData(List<SpecialDemandBean> list, boolean append);

        String getSearchWords();

        String getPurchaserID();
    }

    interface ISpecialDemandListPresenter extends IPresenter<ISpecialDemandListView> {
        void refresh();

        void loadMore();
    }
}
