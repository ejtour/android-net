package com.hll_sc_app.app.blacklist.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationListBean;

import java.util.List;

public interface IBlackDetailContract {


    interface IDetailView extends ILoadView {
        /**
         * 设置view
         */
        void setView(List<OrientationDetailBean> list);

        /**
         * 获取mainID
         */
        String getMainID();
        /**
         * 设置url
         */
        void setPurchaserUrl(String url);
        /**
         * 设置成功
         */
        void addSuccess();
        }

    interface IPresent extends IPresenter<IBlackDetailContract.IDetailView> {
        /**
         * 设置定向售卖
         */
        void setOrientation(List<OrientationDetailBean> list, OrientationListBean bean);
        /**
         * 获取定向售卖数据
         */
        void getOrientation();
        /**
         * 获取集团信息
         */
        void getGroupInfo(String purchaserID);
    }
}
