package com.hll_sc_app.app.analysis;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.LostResp;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public interface IAnalysisContract {
    interface IAnalysisView extends ILoadView {
        void setLostData(LostResp resp);

        void setAnalysisData(AnalysisResp resp);

        void setTopTenData(TopTenResp resp);
    }

    interface IAnalysisPresenter extends IPresenter<IAnalysisView> {

    }
}
