package com.hll_sc_app.app.orientationsale.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.orientation.OrientationListBean;

import java.util.List;

public interface IOrientationListContract {
    interface IOrientationListView extends ILoadView {

        /**
         * 设置view
         */
        void setView(List<OrientationListBean> list, boolean append);
        /**
         * 删除成功
         */
        void delSuccess();
    }

    interface IOrientationListPresenter extends IPresenter<IOrientationListContract.IOrientationListView> {
        /**
         * 获取定向售卖数据
         */
        void refresh();

        void loadMore();
        /**
         * 删除定向售卖分组
         */
        void delOrientation(String mainID);
    }
}
