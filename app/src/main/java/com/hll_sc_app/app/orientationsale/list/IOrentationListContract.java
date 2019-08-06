package com.hll_sc_app.app.orientationsale.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.orientation.OrientationListBean;

import java.util.List;

public interface IOrentationListContract {
    interface IOrentationListView extends ILoadView {

        /**
         * 设置view
         */
        void setView(List<OrientationListBean> list, Integer pageNum);
    }

    interface IOrentationListPresenter extends IPresenter<IOrentationListContract.IOrentationListView> {
        /**
         * 获取定向售卖数据
         */
        void getOrentation(Integer pageNum);
    }
}
