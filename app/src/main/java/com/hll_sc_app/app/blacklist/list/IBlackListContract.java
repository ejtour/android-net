package com.hll_sc_app.app.blacklist.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.orientation.OrientationListBean;

import java.util.List;

public interface IBlackListContract {
    interface IBlackListView extends ILoadView {

        /**
         * 设置view
         */
        void setView(List<OrientationListBean> list, Integer pageNum);
        /**
         * 删除成功
         */
        void delSuccess();
    }

    interface IBlackListPresenter extends IPresenter<IBlackListContract.IBlackListView> {
        /**
         * 获取定向售卖数据
         */
        void getOrientation(Integer pageNum);
        /**
         * 删除定向售卖分组
         */
        void delOrientation(String mainID);
    }
}
