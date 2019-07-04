package com.hll_sc_app.app.goods.relevance;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 第三方商品关联-采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public interface GoodsRelevancePurchaserContract {

    interface IGoodsRelevancePurchaserView extends ILoadView {
        /**
         * 采购商集团名称检索字段
         *
         * @return 搜索词
         */
        String getGroupName();

        /**
         * 来源 1-哗啦啦供应链 2-天财供应链
         *
         * @return 1-哗啦啦供应链 2-天财供应链
         */
        String getResourceType();

        /**
         * 显示来源选择
         */
        void showResourceTypeWindow();

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getName();

        /**
         * 展示采购商集团列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showPurchaserList(List<PurchaserBean> list, boolean append, int total);
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
