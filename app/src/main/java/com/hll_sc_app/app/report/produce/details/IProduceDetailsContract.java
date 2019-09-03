package com.hll_sc_app.app.report.produce.details;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/31
 */

public interface IProduceDetailsContract {
    interface IProduceDetailsView extends ILoadView {
        void setData(List<ProduceDetailBean> list);
    }

    interface IProduceDetailsPresenter extends IPresenter<IProduceDetailsView> {
        void load(boolean showLoading);
    }
}
