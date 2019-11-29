package com.hll_sc_app.app.crm.customer.seas.allot;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.staff.EmployeeBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

public interface ISalesmanAllotContract {
    interface ISalesmanAllotView extends ILoadView {
        void setData(List<EmployeeBean> list, boolean append);

        String getID();

        String getPurchaserID();

        String getSearchWords();

        boolean isIntent();
    }

    interface ISalesmanAllotPresenter extends IPresenter<ISalesmanAllotView> {
        void refresh();

        void loadMore();

        void allot(String id, String name, String phone);
    }
}
