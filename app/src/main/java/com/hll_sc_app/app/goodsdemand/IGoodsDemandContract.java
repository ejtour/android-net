package com.hll_sc_app.app.goodsdemand;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public interface IGoodsDemandContract {
    interface IGoodsDemandView extends ILoadView {
        void handleData(List<GoodsDemandBean> list, boolean append);
    }

    interface IGoodsDemandPresenter extends IPresenter<IGoodsDemandView> {
        void refresh();

        void loadMore();
    }
}
