package com.hll_sc_app.app.report.produce.manhour;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.bean.report.purchase.ManHourReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public interface IManHourSettingContract {
    interface IManHourSettingView extends ILoadView {
        void setData(List<ManHourBean> beans, boolean cost);

        void saveSuccess();
    }

    interface IManHourSettingPresenter extends IPresenter<IManHourSettingView> {
        void save(ManHourReq req);
    }
}
