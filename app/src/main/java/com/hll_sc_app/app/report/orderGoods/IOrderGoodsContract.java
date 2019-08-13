package com.hll_sc_app.app.report.orderGoods;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.impl.IPurchaserContract;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public interface IOrderGoodsContract {
    interface IOrderGoodsView extends IExportView, IPurchaserContract.IPurchaserView {
        void showList(List<OrderGoodsBean> list, boolean append);
    }

    interface IOrderGoodsPresenter extends IPresenter<IOrderGoodsView>, IPurchaserContract.IPurchaserPresenter {
        void getOrderGoods(boolean showLoading);

        void reload();

        void loadMore();

        void refresh();

        void export(String email);
    }
}
