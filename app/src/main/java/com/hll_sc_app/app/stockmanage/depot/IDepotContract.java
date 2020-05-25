package com.hll_sc_app.app.stockmanage.depot;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.DepotResp;

import java.util.List;

public interface IDepotContract {

    interface IDepotView extends ILoadView {
        void setData(List<DepotResp> list, boolean append);

        String getSearchWords();

        void toggleSuccess();

        void defaultIsOk();

        void enableDetail(boolean enable);
    }

    interface IDepotPresenter extends IPresenter<IDepotView> {

        void loadList();

        void refresh();

        void loadMore();

        void setDefault(String id);

        void toggleStatus(String id, int active);
    }
}
