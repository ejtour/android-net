package com.hll_sc_app.app.stockmanage.depot.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.stockmanage.DepotGoodsReq;
import com.hll_sc_app.bean.stockmanage.DepotResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

interface IDepotDetailContract {
    interface IDepotDetailView extends ILoadView {
        void setData(DepotResp resp);

        void setGoodsList(List<GoodsBean> list, boolean append);

        String getSearchWords();

        String getDepotID();

        void removeSuccess();

        void cacheAllGoods(List<GoodsBean> list);

        void saveSuccess();
    }

    interface IDepotDetailPresenter extends IPresenter<IDepotDetailView> {

        void searchGoodsList();

        void refresh();

        void loadMore();

        void delGoods(String goodsID);

        void getAllGoods();

        void saveGoodsList(DepotGoodsReq req);
    }
}
