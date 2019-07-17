package com.hll_sc_app.app.cooperation.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 合作采购商-搜索新增
 *
 * @author zhuyingsong
 * @date 2019/7/17
 */
public interface CooperationAddContract {

    interface ICooperationAddView extends ILoadView {
        /**
         * 展示采购商列表
         *
         * @param list   list
         * @param append true-追加
         */
        void showPurchaserList(List<PurchaserBean> list, boolean append);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface ICooperationAddPresenter extends IPresenter<ICooperationAddView> {
        /**
         * 加载采购商列表
         *
         * @param showLoading true-显示对话框
         */
        void queryPurchaserList(boolean showLoading);

        /**
         * 加载更多采购商列表
         */
        void queryPurchaserList();
    }
}
