package com.hll_sc_app.app.stockmanage.stockquery;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

public interface IStockQueryContract {
    interface IView extends IExportView {
        void getHouseListSuccess(List<HouseBean> houseBeans);

        void getGoodsListSuccess(List<GoodsBean> goodsBeans, boolean isMore);

        String getHouseId();

        String getProductName();
    }

    interface IPresent extends IPresenter<IView> {
        void queryHouseList();

        void queryGoodsInvList(boolean isLoading);

        void refreshGoodsList();

        void getMoreGoodsList();

        void search();

        int getPageSize();

        void export(String email);
    }
}
