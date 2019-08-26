package com.hll_sc_app.app.bill.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.bill.BillBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public interface IBillDetailContract {
    interface IBillDetailView extends ILoadView{
        void updateData(BillBean bean);
        void actionSuccess();
    }

    interface IBillDetailPresenter extends IPresenter<IBillDetailView>{
        void doAction();
    }
}
