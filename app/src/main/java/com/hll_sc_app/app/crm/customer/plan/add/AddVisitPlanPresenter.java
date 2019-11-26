package com.hll_sc_app.app.crm.customer.plan.add;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

public class AddVisitPlanPresenter implements IAddVisitPlanContract.IAddVisitPlanPresenter {
    private IAddVisitPlanContract.IAddVisitPlanView mView;

    private AddVisitPlanPresenter() {
    }

    public static AddVisitPlanPresenter newInstance() {
        return new AddVisitPlanPresenter();
    }

    @Override
    public void save(VisitPlanBean bean) {
        Customer.saveVisitPlan(bean, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void register(IAddVisitPlanContract.IAddVisitPlanView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
