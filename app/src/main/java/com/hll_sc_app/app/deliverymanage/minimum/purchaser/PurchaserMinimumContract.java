package com.hll_sc_app.app.deliverymanage.minimum.purchaser;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 选择合作采购商-最小起购量设置
 *
 * @author zhuyingsong
 * @date 2019/7/31
 */
public interface PurchaserMinimumContract {

    interface IPurchaserMinimumView extends ILoadView {
        /**
         * 展示合作采购商列表
         *
         * @param list   list
         * @param append 追加
         */
        void showPurchaserList(List<PurchaserBean> list, boolean append);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface IPurchaserMinimumPresenter extends IPresenter<IPurchaserMinimumView> {
        /**
         * 查询合作采购商列表
         *
         * @param showLoading 展示 loading
         */
        void queryPurchaserList(boolean showLoading);

        /**
         * 查询更多合作采购商列表
         */
        void queryMorePurchaserList();
    }
}
