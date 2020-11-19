package com.hll_sc_app.app.aptitude.goods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

interface IAptitudeGoodsContract {
    interface IAptitudeGoodsView extends ILoadView {
        void setData(List<GoodsBean> list, boolean append);

        boolean isChecked();

        String getSearchWords();
    }

    interface IAptitudeGoodsPresenter extends IPresenter<IAptitudeGoodsView> {
        void refresh();

        void loadMore();
    }
}
