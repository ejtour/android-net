package com.hll_sc_app.app.goods.invwarn;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
public class GoodsInvWarnPresenter implements GoodsInvWarnContract.IGoodsInvWarnPresenter {
    private GoodsInvWarnContract.IGoodsInvWarnView mView;

    static GoodsInvWarnPresenter newInstance() {
        return new GoodsInvWarnPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(GoodsInvWarnContract.IGoodsInvWarnView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
