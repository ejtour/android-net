package com.hll_sc_app.app.crm.customer.search.plan;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.VisitPlanBean;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

public interface ISearchPlanContract {
    interface ISearchPlanView extends ILoadView {
        void setData(List<VisitPlanBean> list, boolean append);

        String getSearchWords();

        int getCustomerType();

        Date getStartDate();

        Date getEndDate();
    }

    interface ISearchPlanPresenter extends IPresenter<ISearchPlanView> {
        void refresh();

        void loadMore();
    }
}
