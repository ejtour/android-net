package com.hll_sc_app.app.goods.add.specs;

import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 新增商品规格
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public class GoodsSpecsAddPresenter implements GoodsSpecsAddContract.IGoodsAddPresenter {
    private GoodsSpecsAddContract.IGoodsAddView mView;
    private CategoryResp mCategoryResp;

    static GoodsSpecsAddPresenter newInstance() {
        return new GoodsSpecsAddPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsSpecsAddContract.IGoodsAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
