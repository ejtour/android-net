package com.hll_sc_app.app.crm.customer.record;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.VisitRecordBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public interface IVisitRecordContract {
    interface IVisitRecordView extends ILoadView {
        void setData(List<VisitRecordBean> list, boolean append);

        boolean isAll();

        String getSearchWords();

        void delSuccess();
    }

    interface IVisitRecordPresenter extends IPresenter<IVisitRecordView> {
        void refresh();

        void loadMore();

        void delRecord(String id);
    }
}
