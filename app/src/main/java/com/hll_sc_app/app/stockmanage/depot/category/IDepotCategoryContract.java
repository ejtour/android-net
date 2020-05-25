package com.hll_sc_app.app.stockmanage.depot.category;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.stockmanage.DepotCategoryReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/15
 */

interface IDepotCategoryContract {
    interface IDepotCategoryView extends ILoadView {
        void success();

        void setData(List<CustomCategoryBean> list);

        List<String> getSelectedIDList();
    }

    interface IDepotCategoryPresenter extends IPresenter<IDepotCategoryView> {
        void save(DepotCategoryReq req);
    }
}
