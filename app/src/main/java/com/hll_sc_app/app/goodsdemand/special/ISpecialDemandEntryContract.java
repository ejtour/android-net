package com.hll_sc_app.app.goodsdemand.special;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandEntryBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public interface ISpecialDemandEntryContract {
    interface ISpecialDemandEntryView extends ILoadView {
        void setData(List<SpecialDemandEntryBean> list, boolean append);

        String getSearchWords();
    }

    interface ISpecialDemandEntryPresenter extends IPresenter<ISpecialDemandEntryView> {
        void refresh();

        void loadMore();
    }
}
