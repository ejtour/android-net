package com.hll_sc_app.app.goods.template;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

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
    }
}
