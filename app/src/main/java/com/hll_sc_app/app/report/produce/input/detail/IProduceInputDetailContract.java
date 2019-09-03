package com.hll_sc_app.app.report.produce.input.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.purchase.ManHourBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public interface IProduceInputDetailContract {
    interface IProduceInputDetailView extends ILoadView{
        void setCompanyNameData(List<ManHourBean> beanList);
    }

    interface IProduceInputDetailPresenter extends IPresenter<IProduceInputDetailView>{

    }
}
