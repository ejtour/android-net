package com.hll_sc_app.app.price.local;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.LocalPriceBean;
import com.hll_sc_app.bean.price.MarketBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/29
 */

public interface IPriceLocalContract {
    interface IPriceLocalView extends ILoadView {
        void handleMarket(List<MarketBean> list);

        void cacheCategory(List<CategoryBean> list);

        void setData(List<LocalPriceBean> list, boolean append);

        String getProvinceCode();

        String getMarketCode();

        String getCategoryCode();
    }

    interface IPriceLocalPresenter extends IPresenter<IPriceLocalView> {
        void queryMarket(String provinceCode);

        void loadList();

        void queryCategory();

        void refresh();

        void loadMore();
    }
}
