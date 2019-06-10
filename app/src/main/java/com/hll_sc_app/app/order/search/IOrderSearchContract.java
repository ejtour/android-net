package com.hll_sc_app.app.order.search;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.search.OrderSearchBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public interface IOrderSearchContract {
    interface IOrderSearchView extends ILoadView {
        /**
         * 刷新搜索数据
         *
         * @param list 搜索数据列表
         */
        void refreshSearchData(List<OrderSearchBean> list);
    }

    interface IOrderSearchPresenter extends IPresenter<IOrderSearchView> {
        /**
         * 请求搜索
         *
         * @param searchWords 搜索词
         */
        void requestSearch(String searchWords);
    }
}
