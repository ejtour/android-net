package com.hll_sc_app.app.report.produce.input.maneffect;

import com.hll_sc_app.bean.report.produce.ProduceBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/31
 */

public class PeopleEffectInputPresenter implements IPeopleEffectInputContract.IPeopleEffectInputPresenter {
    private IPeopleEffectInputContract.IPeopleEffectInputView mView;
    private ProduceBean mBean;

    public static PeopleEffectInputPresenter newInstance(ProduceBean bean) {
        return new PeopleEffectInputPresenter(bean);
    }

    private PeopleEffectInputPresenter(ProduceBean bean) {
        mBean = bean;
    }

    @Override
    public void save() {

    }

    @Override
    public void register(IPeopleEffectInputContract.IPeopleEffectInputView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
