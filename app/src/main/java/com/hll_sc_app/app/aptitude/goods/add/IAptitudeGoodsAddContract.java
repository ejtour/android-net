package com.hll_sc_app.app.aptitude.goods.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/1/20.
 */
interface IAptitudeGoodsAddContract {
    interface IAptitudeGoodsAddView extends ILoadView {
        void setData(List<GoodsBean> list, boolean append);

        String getSearchWords();
    }

    interface IAptitudeGoodsAddPresenter extends IPresenter<IAptitudeGoodsAddView> {
        void refresh();

        void loadMore();
    }
}
