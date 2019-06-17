package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
public class GoodsAddPresenter implements GoodsAddContract.IGoodsAddPresenter {
    private GoodsAddContract.IGoodsAddView mView;

    static GoodsAddPresenter newInstance() {
        return new GoodsAddPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsAddContract.IGoodsAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
