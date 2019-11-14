package com.hll_sc_app.app.crm.daily.edit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.staff.EmployeeBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/14
 */

public interface ICrmDailyEditContract {
    interface ICrmDailyEditView extends ILoadView {
        void updateLastCommit(List<DailyBean> list);

        void success();

        void cacheReceiverList(List<EmployeeBean> list);
    }

    interface ICrmDailyEditPresenter extends IPresenter<ICrmDailyEditView> {
        void submit();

        void reqReceiverList();
    }
}
