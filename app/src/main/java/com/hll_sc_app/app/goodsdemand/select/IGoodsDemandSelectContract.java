package com.hll_sc_app.app.goodsdemand.select;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/24
 */

public interface IGoodsDemandSelectContract {
    interface IGoodsDemandSelectView extends ILoadView {
        void setData(List<GoodsBean> list, boolean append);

        String getSearchWords();

        String getID();

        String getPurchaserID();

        void success();
    }

    interface IGoodsDemandSelectPresenter extends IPresenter<IGoodsDemandSelectView> {
        void refresh();

        void loadMore();

        void confirm(GoodsBean bean);
    }
}
