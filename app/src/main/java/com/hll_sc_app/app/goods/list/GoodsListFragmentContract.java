package com.hll_sc_app.app.goods.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;


/**
 * 首页商品管理列表Fragment
 *
 * @author 朱英松
 * @date 2018/6/11
 */
interface GoodsListFragmentContract {

    interface IGoodsListView extends ILoadView {

        /**
         * 搜索词
         *
         * @return 搜索词
         */
        String getName();

        /**
         * 上下架
         *
         * @return 上下架
         */
        String getProductStatus();

        /**
         * 商品类型
         *
         * @return 商品类型
         */
        String getActionType();

        /**
         * 展示商品列表
         *
         * @param list   列表
         * @param append 追加
         */
        void showList(List<GoodsBean> list, boolean append);

    }

    interface IGoodsListPresenter extends IPresenter<IGoodsListView> {
        /**
         * 查询商品列表
         *
         * @param showLoading true显示加载对话框
         */
        void queryGoodsList(boolean showLoading);

        /**
         * 加载更多商品列表
         */
        void queryMoreGoodsList();
    }
}
