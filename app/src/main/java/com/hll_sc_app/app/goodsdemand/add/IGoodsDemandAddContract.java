package com.hll_sc_app.app.goodsdemand.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.window.NameValue;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public interface IGoodsDemandAddContract {
    interface IGoodsDemandAddView extends ILoadView {
        void setData(NameValue value);
    }

    interface IGoodsDemandAddPresenter extends IPresenter<IGoodsDemandAddView> {

    }
}
