package com.hll_sc_app.app.report.orderGoods.detail;

import com.hll_sc_app.app.order.details.IOrderDetailContract;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public interface IOrderGoodsDetailContract {
    interface IOrderGoodsDetailView extends ILoadView {
        void setList(List<OrderGoodsDetailBean> beans, boolean append);
    }

    interface IOrderGoodsDetailPresenter extends IPresenter<IOrderGoodsDetailContract.IOrderGoodsDetailView> {
        void refresh();

        void loadMore();
    }
}
