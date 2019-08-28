package com.hll_sc_app.app.inspection.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.inspection.InspectionBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public interface IInspectionDetailContract {
    interface IInspectionDetailView extends ILoadView{
        void updateData(InspectionBean bean);
    }

    interface IInspectionDetailPresenter extends IPresenter<IInspectionDetailView>{
        
    }
}
