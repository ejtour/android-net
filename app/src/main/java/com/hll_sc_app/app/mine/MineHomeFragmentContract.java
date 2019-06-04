package com.hll_sc_app.app.mine;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;


/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
interface MineHomeFragmentContract {

    interface IHomeView extends ILoadView {

    }

    interface IHomePresenter extends IPresenter<IHomeView> {
    }
}
