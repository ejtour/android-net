package com.hll_sc_app.app.goods.add.customcategory;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;

/**
 * 自定义分类
 *
 * @author zhuyingsong
 * @date 2019/6/18
 */
public interface GoodsCustomCategoryContract {

    interface IGoodsCustomCategoryView extends ILoadView {
        /**
         * 显示自定义分类
         *
         * @param resp 自定义分类
         */
        void showCustomCategoryList(CustomCategoryResp resp);
    }

    interface IGoodsCustomCategoryPresenter extends IPresenter<IGoodsCustomCategoryView> {
        /**
         * 查询自定义分类
         */
        void queryCustomCategory();

        /**
         * 删除自定义分类
         *
         * @param bean CustomCategoryBean
         */
        void delCustomCategory(CustomCategoryBean bean);
    }
}
