package com.hll_sc_app.app.report.produce.input;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.bean.report.produce.ProduceInputReq;
import com.hll_sc_app.bean.report.purchase.ManHourBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public interface IProduceInputContract {
    interface IProduceInputView extends ILoadView {
        void setShiftData(List<ManHourBean> beanList);

        void setData(List<ProduceDetailBean> list);

        void saveSuccess();
    }

    interface IProduceInputPresenter extends IPresenter<IProduceInputView> {
        void save(ProduceInputReq req);

        void reqShiftList();
    }
}
