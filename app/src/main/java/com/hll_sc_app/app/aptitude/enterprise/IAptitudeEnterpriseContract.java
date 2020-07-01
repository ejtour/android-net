package com.hll_sc_app.app.aptitude.enterprise;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aptitude.AptitudeEnterpriseBean;

import java.util.List;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/6/29
 */

interface IAptitudeEnterpriseContract {
    interface IAptitudeEnterpriseView extends ILoadView {
        void setData(List<AptitudeEnterpriseBean> list);
    }

    interface IAptitudeEnterprisePresenter extends IPresenter<IAptitudeEnterpriseView> {
        void delete(AptitudeEnterpriseBean bean);
    }
}
