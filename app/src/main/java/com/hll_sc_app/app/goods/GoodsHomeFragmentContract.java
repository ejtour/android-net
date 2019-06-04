package com.hll_sc_app.app.goods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;


/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
interface GoodsHomeFragmentContract {

    interface IHomeView extends ILoadView {

    }

    interface IHomePresenter extends IPresenter<IHomeView> {
    }
}
