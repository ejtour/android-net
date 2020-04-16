package com.hll_sc_app.app.bill.log;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.bill.BillLogBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */

interface IBillLogContract {
    interface IBillLogView extends ILoadView {
        void setData(List<BillLogBean> list);

        String getID();
    }

    interface IBillLogPresenter extends IPresenter<IBillLogView> {

    }
}
