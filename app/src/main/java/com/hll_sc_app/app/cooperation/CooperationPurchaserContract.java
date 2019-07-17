package com.hll_sc_app.app.cooperation;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;

/**
 * 合作采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface CooperationPurchaserContract {

    interface IGoodsRelevancePurchaserView extends ILoadView {
        /**
         * 采购商集团名称检索字段
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 展示采购商集团列表
         *
         * @param resp   list
         * @param append true-追加
         */
        void showPurchaserList(CooperationPurchaserResp resp, boolean append);
    }

    interface IGoodsRelevancePurchaserPresenter extends IPresenter<IGoodsRelevancePurchaserView> {
        /**
         * 查询商品关联的采购商列表
         *
         * @param showLoading true-显示 loading
         */
        void queryPurchaserList(boolean showLoading);

        /**
         * 查询下一页商品关联的采购商列表
         */
        void queryMorePurchaserList();
    }
}
