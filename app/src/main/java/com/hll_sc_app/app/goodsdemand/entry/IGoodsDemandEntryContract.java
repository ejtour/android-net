package com.hll_sc_app.app.goodsdemand.entry;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public interface IGoodsDemandEntryContract {
    interface IGoodsDemandEntryView extends ILoadView {
        void handleData(List<GoodsDemandBean> list, boolean append);
    }

    interface IGoodsDemandEntryPresenter extends IPresenter<IGoodsDemandEntryView> {
        void refresh();

        void loadMore();
    }
}
