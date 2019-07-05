package com.hll_sc_app.app.goods.relevance.goods.select;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.DepositProductBean;
import com.hll_sc_app.bean.user.CategoryResp;

import java.util.List;

/**
 * 第三方商品关联-选择关联商品
 *
 * @author zhuyingsong
 * @date 2019/7/5
 */
public interface GoodsRelevanceSelectContract {

    interface IGoodsStickView extends ILoadView {
        /**
         * 获取商品名称
         *
         * @return goodsName
         */
        String getProductName();

        /**
         * 获取商品编码
         *
         * @return 商品编码
         */
        String getProductCode();


        /**
         * 显示商城二级分类
         *
         * @param resp 自定义分类
         */
        void showCategoryList(CategoryResp resp);

        /**
         * 展示商品列表
         *
         * @param list   列表
         * @param append 追加
         * @param total  总量
         */
        void showList(List<DepositProductBean> list, boolean append, int total);

        /**
         * 获取分类 ID
         *
         * @return ID
         */
        String getCategorySubId();

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getName();
    }

    interface IGoodsStickPresenter extends IPresenter<IGoodsStickView> {
        /**
         * 查询自定义分类
         */
        void queryCategory();

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
