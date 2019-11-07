package com.hll_sc_app.app.setting.tax.goodsselect;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.user.CategoryItem;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public interface IGoodsSelectContract {
    interface IGoodsSelectView extends ILoadView {
        void setCategory(List<CategoryItem> list);

        void setData(List<GoodsBean> list, boolean append);

        String getID();

        String getSearchWords();
    }

    interface IGoodsSelectPresenter extends IPresenter<IGoodsSelectView> {
        void reload();
        void refresh();
        void loadMore();
    }
}
