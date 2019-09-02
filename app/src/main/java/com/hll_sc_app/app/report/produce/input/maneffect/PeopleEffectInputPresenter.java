package com.hll_sc_app.app.report.produce.input.maneffect;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.produce.ProduceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

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
    public void save(String packageNum, String weighNum) {
        Report.recordPeopleEffect(mBean.getDate(), packageNum, weighNum, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void register(IPeopleEffectInputContract.IPeopleEffectInputView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
