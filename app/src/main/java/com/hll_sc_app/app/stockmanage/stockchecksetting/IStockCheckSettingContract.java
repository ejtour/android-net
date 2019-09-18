package com.hll_sc_app.app.stockmanage.stockchecksetting;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public interface IStockCheckSettingContract {
    interface IView extends ILoadView {
        String getSearchContent();

        void queryGoodsSuccess(List<GoodsBean> goodsBeans, boolean isMore);

        ArrayList<String> getProductIds();

        void addSuccess();

        void removeSuccess();

    }

    interface IPresent extends IPresenter<IView> {

        void queryGoodsResp(boolean isLoading);

        void getMore();

        void refresh();

        void changeStockCheckSetting(String actionType, List<String> ids);

        void remove();

        void add(List<String> ids);

        int getPageSize();

    }
}
