package com.hll_sc_app.app.crm.customer.seas.detail.visit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.VisitRecordBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public interface ICustomerSeasVisitContract {
    interface ICustomerSeasVisitView extends ILoadView {
        void setData(List<VisitRecordBean> list, boolean append);

        String getID();
    }

    interface ICustomerSeasVisitPresenter extends IPresenter<ICustomerSeasVisitView> {
        void refresh();

        void loadMore();
    }
}
