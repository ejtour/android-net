package com.hll_sc_app.app.mine;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;


/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
interface MineHomeFragmentContract {

    interface IHomeView extends ILoadView {
        void setData(AnalysisBean bean);
    }

    interface IHomePresenter extends IPresenter<IHomeView> {
        void refresh();
    }
}
