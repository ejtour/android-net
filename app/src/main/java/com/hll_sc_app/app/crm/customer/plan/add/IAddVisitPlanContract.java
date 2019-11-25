package com.hll_sc_app.app.crm.customer.plan.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.VisitPlanBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

public interface IAddVisitPlanContract {
    interface IAddVisitPlanView extends ILoadView{
        void saveSuccess();
    }

    interface IAddVisitPlanPresenter extends IPresenter<IAddVisitPlanView>{
        void save(VisitPlanBean bean);
    }
}
