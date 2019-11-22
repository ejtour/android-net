package com.hll_sc_app.app.crm.customer.record.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.VisitRecordBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/22
 */

public interface IAddVisitRecordContract {
    interface IAddVisitRecordView extends ILoadView {
        void saveSuccess();
    }

    interface IAddVisitRecordPresenter extends IPresenter<IAddVisitRecordView> {
        void save(VisitRecordBean bean);
    }
}
