package com.hll_sc_app.app.invoice.detail.record;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

public interface IReturnRecordContract {
    interface IReturnRecordView extends ILoadView {
        void success();
    }

    interface IReturnRecordPresenter extends IPresenter<IReturnRecordView> {
        void commit(String id, String time, String money, String type);
    }
}
