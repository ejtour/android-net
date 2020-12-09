package com.hll_sc_app.app.aptitude.goods.detail;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 11/30/20.
 */
class AptitudeGoodsDetailPresenter implements IAptitudeGoodsDetailContract.IAptitudeGoodsDetailPresenter {
    private IAptitudeGoodsDetailContract.IAptitudeGoodsDetailView mView;

    public static AptitudeGoodsDetailPresenter newInstance() {
        return new AptitudeGoodsDetailPresenter();
    }

    private AptitudeGoodsDetailPresenter() {
    }

    @Override
    public void start() {
        Aptitude.queryGoodsAptitude(mView.getID(), new SimpleObserver<AptitudeBean>(mView) {
            @Override
            public void onSuccess(AptitudeBean bean) {
                mView.setData(bean);
            }
        });
    }

    @Override
    public void save(AptitudeBean bean) {
        Aptitude.saveGoodsAptitude(bean, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void register(IAptitudeGoodsDetailContract.IAptitudeGoodsDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
