package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
public interface GoodsAddContract {

    interface IGoodsAddView extends ILoadView {

    }

    interface IGoodsAddPresenter extends IPresenter<IGoodsAddView> {
    }
}
