package com.hll_sc_app.app.marketingsetting.selectproduct;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.user.CategoryResp;

import java.util.List;

/**
 * 报价单-新增商品
 *
 * @author zhuyingsong
 * @date 2019/7/10
 */
public interface IProductSelectContract {

    interface IGoodsStickView extends ILoadView {

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
         */
        void showList(List<SkuGoodsBean> list, boolean append);

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


        /**
         * 获取跳转页面名称
         */
        String getActivityName();
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
