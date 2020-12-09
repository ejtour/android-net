package com.hll_sc_app.app.aptitude.goods;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

class AptitudeGoodsPresenter implements IAptitudeGoodsContract.IAptitudeGoodsPresenter {
    private IAptitudeGoodsContract.IAptitudeGoodsView mView;

    public static AptitudeGoodsPresenter newInstance() {
        return new AptitudeGoodsPresenter();
    }

    private AptitudeGoodsPresenter() {
    }

    @Override
    public void start() {
        load(true);
    }

    private void load(boolean showLoading) {
        Aptitude.queryGoodsAptitudeList(mView.getReq()
                .create(), new SimpleObserver<SingleListResp<AptitudeBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<AptitudeBean> aptitudeBeanSingleListResp) {
                mView.setData(aptitudeBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void refresh() {
        load(false);
    }

    @Override
    public void register(IAptitudeGoodsContract.IAptitudeGoodsView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void delAptitude(String id) {
        Aptitude.delGoodsAptitude(id, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.delSuccess();
            }
        });
    }
}
