package com.hll_sc_app.app.aptitude;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeExpireResp;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/6/29
 */

public class AptitudePresenter implements IAptitudeContract.IAptitudePresenter {
    private IAptitudeContract.IAptitudeView mView;

    private AptitudePresenter() {
    }

    public static AptitudePresenter newInstance() {
        return new AptitudePresenter();
    }

    @Override
    public void start() {
        Aptitude.queryExpireRemain(1, new SimpleObserver<AptitudeExpireResp>(mView) {
            @Override
            public void onSuccess(AptitudeExpireResp aptitudeExpireResp) {
                if (aptitudeExpireResp.isHasExpiration()) {
                    mView.expireTip(aptitudeExpireResp.getReminderMessage());
                }
            }
        });
        loadList();
    }

    @Override
    public void register(IAptitudeContract.IAptitudeView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void loadList() {
        Aptitude.queryAptitudeList(mView.in30Day(), new SimpleObserver<SingleListResp<AptitudeBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<AptitudeBean> aptitudeEnterpriseBeanSingleListResp) {
                mView.setData(aptitudeEnterpriseBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void save(AptitudeReq req) {
        Aptitude.saveAptitudeList(req, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }
}
