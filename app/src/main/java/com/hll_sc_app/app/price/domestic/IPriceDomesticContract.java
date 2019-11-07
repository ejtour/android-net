package com.hll_sc_app.app.price.domestic;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.DomesticPriceBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/29
 */

public interface IPriceDomesticContract {
    interface IPriceDomesticView extends ILoadView {
        String getDate();

        String getCategory();

        int getSortType();

        void cacheCategory(List<CategoryBean> list);

        void setData(List<DomesticPriceBean> list);
    }

    interface IPriceDomesticPresenter extends IPresenter<IPriceDomesticView> {
        void loadList();
    }
}
