package com.hll_sc_app.app.goods.stick;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryResp;

/**
 * 商品置顶管理
 *
 * @author zhuyingsong
 * @date 2019/7/1
 */
public interface GoodsStickContract {

    interface IGoodsStickView extends ILoadView {
        /**
         * 显示自定义分类
         *
         * @param resp 自定义分类
         */
        void showCustomCategoryList(CustomCategoryResp resp);
    }

    interface IGoodsStickPresenter extends IPresenter<IGoodsStickView> {
        /**
         * 查询自定义分类
         */
        void queryCustomCategory();
    }
}
