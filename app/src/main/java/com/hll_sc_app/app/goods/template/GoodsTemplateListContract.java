package com.hll_sc_app.app.goods.template;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.user.CategoryResp;

import java.util.List;

/**
 * 从商品库导入
 *
 * @author zhuyingsong
 * @date 2019/6/27
 */
public interface GoodsTemplateListContract {

    interface IGoodsTemplateListView extends ILoadView {
        /**
         * 展示商品模板列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showGoodsTemplateList(List<GoodsBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchContent();

        /**
         * 获取品牌名
         *
         * @return 品牌名
         */
        String getBrandName();

        /**
         * 获取产地
         *
         * @return 产地
         */
        String getProductPlace();

        /**
         * 显示分类筛选框
         *
         * @param resp resp
         */
        void showCategoryFilterWindow(CategoryResp resp);

        /**
         * 显示行业标签
         *
         * @param list list
         */
        void showLabelFilterWindow(List<LabelBean> list);
    }

    interface IGoodsTemplateListPresenter extends IPresenter<IGoodsTemplateListView> {
        /**
         * 查询品牌列表
         *
         * @param showLoading true-显示对话框
         */
        void queryGoodsTemplateList(boolean showLoading);

        /**
         * 查询更多品牌列表
         */
        void queryMoreGoodsTemplateList();

        /**
         * 获取分类列表
         */
        void queryCategory();

        /**
         * 行业标签查询接口
         */
        void queryLabelList();
    }
}
