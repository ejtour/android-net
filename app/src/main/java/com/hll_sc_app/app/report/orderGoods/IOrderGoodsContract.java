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

        /**
         * 绑定邮箱
         */
        void bindEmail();

        /**
         * 导出成功
         *
         * @param email 邮箱
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param msg 失败消息
         */
        void exportFailure(String msg);
    }

    interface IOrderGoodsPresenter extends IPresenter<IOrderGoodsView> {
        void getPurchaserList(String searchWords);

        void getOrderGoodsDetails(boolean showLoading);

        void loadMore();

        void refresh();

        void export(String email);
    }
}
