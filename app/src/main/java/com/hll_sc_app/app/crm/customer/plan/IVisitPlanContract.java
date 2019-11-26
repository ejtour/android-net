package com.hll_sc_app.app.crm.customer.plan;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.VisitPlanBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/23
 */

public interface IVisitPlanContract {
    interface IVisitPlanView extends ILoadView {
        void setData(List<VisitPlanBean> list, boolean append);

        void delSuccess();

        String getSearchWords();

        boolean isAll();
    }

    interface IVisitPlanPresenter extends IPresenter<IVisitPlanView> {
        void refresh();

        void loadMore();

        void delPlan(String id);
    }
}
