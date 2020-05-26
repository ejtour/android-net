package com.hll_sc_app.app.stockmanage.selectproduct;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;
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
        void showCategoryList(CustomCategoryResp resp);

        /**
         * 展示商品列表
         *
         * @param list   列表
         * @param append 追加
         */
        void showList(List<GoodsBean> list, boolean append);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getName();

        String getActionType();

        /*获取二级分类*/
        String getCategorySubID();

        String getCategoryThreeID();
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
