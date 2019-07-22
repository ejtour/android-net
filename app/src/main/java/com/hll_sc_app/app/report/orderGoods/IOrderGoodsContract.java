package com.hll_sc_app.app.report.orderGoods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public interface IOrderGoodsContract {
    interface IOrderGoodsView extends ILoadView {
        void showList(List<OrderGoodsBean> list, boolean append);
    }

    interface IOrderGoodsPresenter extends IPresenter<IOrderGoodsView> {
        void getPurchaserList(String searchWords);

        void loadMore();

        void refresh();
    }
}
