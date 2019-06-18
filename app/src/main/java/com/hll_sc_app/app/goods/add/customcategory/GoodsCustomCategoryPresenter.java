package com.hll_sc_app.app.goods.add.customcategory;

import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 自定义分类
 *
 * @author zhuyingsong
 * @date 2019/6/18
 */
public class GoodsCustomCategoryPresenter implements GoodsCustomCategoryContract.IGoodsCustomCategoryPresenter {
    private GoodsCustomCategoryContract.IGoodsCustomCategoryView mView;
    private CategoryResp mCategoryResp;

    static GoodsCustomCategoryPresenter newInstance() {
        return new GoodsCustomCategoryPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsCustomCategoryContract.IGoodsCustomCategoryView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
