package com.hll_sc_app.app.aptitude.goods.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aptitude.AptitudeBean;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 11/30/20.
 */
interface IAptitudeGoodsDetailContract {
    interface IAptitudeGoodsDetailView extends ILoadView {
        String getID();

        void setData(AptitudeBean bean);

        void saveSuccess();
    }

    interface IAptitudeGoodsDetailPresenter extends IPresenter<IAptitudeGoodsDetailView> {
        void save(AptitudeBean bean);
    }
}
