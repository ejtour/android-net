package com.hll_sc_app.app.crm.customer.intent.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.customer.VisitRecordBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public interface ICustomerDetailContract {
    interface ICustomerDetailView extends ILoadView {
        void updateData(CustomerBean bean);

        void setData(List<VisitRecordBean> list, boolean append);

        void loadError(UseCaseException e);

        String getID();
    }

    interface ICustomerDetailPresenter extends IPresenter<ICustomerDetailView> {
        void refresh();

        void loadMore();

        void reload();
    }
}
