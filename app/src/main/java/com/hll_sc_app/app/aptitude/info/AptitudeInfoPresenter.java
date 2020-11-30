package com.hll_sc_app.app.aptitude.info;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeInfoPresenter implements IAptitudeInfoContract.IAptitudeInfoPresenter {

    public static AptitudeInfoPresenter newInstance() {
        return new AptitudeInfoPresenter();
    }

    private IAptitudeInfoContract.IAptitudeInfoView mView;

    private AptitudeInfoPresenter() {
    }

    @Override
    public void save(AptitudeInfoReq req) {
        if (req == null) return;
        Aptitude.saveBaseInfo(req, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void start() {
        Aptitude.queryBaseInfo(new SimpleObserver<AptitudeInfoResp>(mView) {
            @Override
            public void onSuccess(AptitudeInfoResp resp) {
                mView.setData(resp);
            }
        });
    }

    @Override
    public void register(IAptitudeInfoContract.IAptitudeInfoView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
