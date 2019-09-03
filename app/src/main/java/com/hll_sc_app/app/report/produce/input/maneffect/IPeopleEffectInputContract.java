package com.hll_sc_app.app.report.produce.input.maneffect;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/31
 */

public interface IPeopleEffectInputContract {
    interface IPeopleEffectInputView extends ILoadView{
        void saveSuccess();
    }

    interface IPeopleEffectInputPresenter extends IPresenter<IPeopleEffectInputView>{
        void save(String packageNum, String weighNum);
    }
}
