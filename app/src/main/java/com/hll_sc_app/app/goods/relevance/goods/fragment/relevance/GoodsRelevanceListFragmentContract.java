package com.hll_sc_app.app.goods.relevance.goods.fragment.relevance;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsRelevanceBean;

import java.util.List;


/**
 * 第三方商品关联-采购商列表-关联商品列表-未关联、已关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public interface GoodsRelevanceListFragmentContract {

    interface IGoodsRelevanceListView extends ILoadView {
        /**
         * 采购商 id
         *
         * @return 采购商 id
         */
        String getGroupId();

        /**
         * 采购商来源
         *
         * @return 1-哗啦啦供应链 2-天财供应链
         */
        String getResourceType();

        /**
         * 经营模式
         *
         * @return 0：集团模式 1：单店模式
         */
        String getOperateModel();

        /**
         * 获取商品名称搜索词
         *
         * @return 搜索词
         */
        String getGoodsName();

        /**
         * 展示未关联商品列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showGoodsList(List<GoodsRelevanceBean> list, boolean append, int total);
    }

    interface IGoodsRelevanceListPresenter extends IPresenter<IGoodsRelevanceListView> {
        /**
         * 查询未关联商品列表
         *
         * @param showLoading true-是否显示加载框
         */
        void queryGoodsUnRelevanceList(boolean showLoading);

        /**
         * 查询下一页未关联商品列表
         */
        void queryMoreGoodsUnRelevanceList();
    }
}
