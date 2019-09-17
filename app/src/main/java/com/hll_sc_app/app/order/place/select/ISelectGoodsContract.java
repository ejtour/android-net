package com.hll_sc_app.app.order.place.select;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.order.place.GoodsCategoryBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public interface ISelectGoodsContract {
    interface ISelectGoodsView extends ILoadView {
        void setCategoryInfo(List<GoodsCategoryBean> list);

        void setGoodsList(List<GoodsBean> list);
    }

    interface ISelectGoodsPresenter extends IPresenter<ISelectGoodsView> {
        void refresh();

        void loadMore();

        void loadList();
    }
}
