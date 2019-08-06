package com.hll_sc_app.app.warehouse.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 代仓管理-新签代仓客户
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public interface WarehouseAddContract {

    interface IWarehouseAddView extends ILoadView {
        /**
         * 展示客户列表
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

    interface IWarehouseAddPresenter extends IPresenter<IWarehouseAddView> {
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
