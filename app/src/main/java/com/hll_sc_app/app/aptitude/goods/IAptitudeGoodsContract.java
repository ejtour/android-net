package com.hll_sc_app.app.aptitude.goods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.aptitude.AptitudeBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

interface IAptitudeGoodsContract {
    interface IAptitudeGoodsView extends ILoadView {
        void setData(List<AptitudeBean> list);

        BaseMapReq.Builder getReq();

        void delSuccess();

        void expireTip(String msg);
    }

    interface IAptitudeGoodsPresenter extends IPresenter<IAptitudeGoodsView> {
        void load(boolean showLoading);

        void delAptitude(String id);
    }
}
