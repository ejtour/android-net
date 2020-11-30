package com.hll_sc_app.app.aptitude.type;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aptitude.AptitudeBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/26
 */
interface IAptitudeTypeContract {
    interface IAptitudeTypeView extends ILoadView {
        void setData(List<AptitudeBean> list);

        void delFailure();
    }

    interface IAptitudeTypePresenter extends IPresenter<IAptitudeTypeView> {
        void edit(String id, String name);
    }
}
