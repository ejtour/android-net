package com.hll_sc_app.app.aptitude.enterprise;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeEnterpriseBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/6/29
 */

class AptitudeEnterprisePresenter implements IAptitudeEnterpriseContract.IAptitudeEnterprisePresenter {
    private IAptitudeEnterpriseContract.IAptitudeEnterpriseView mView;

    private AptitudeEnterprisePresenter() {
    }

    public static AptitudeEnterprisePresenter newInstance() {
        return new AptitudeEnterprisePresenter();
    }

    @Override
    public void start() {
        Aptitude.queryEnterpriseInfo(new SimpleObserver<SingleListResp<AptitudeEnterpriseBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<AptitudeEnterpriseBean> aptitudeEnterpriseBeanSingleListResp) {
                mView.setData(aptitudeEnterpriseBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void register(IAptitudeEnterpriseContract.IAptitudeEnterpriseView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void delete(AptitudeEnterpriseBean bean) {
        bean = bean.deepCopy();
        bean.setAptitudeName(null);
        bean.setAptitudeUrl(null);
        bean.setEndTime(null);
        Aptitude.editEnterpriseInfo(bean, new SimpleObserver<MsgWrapper<Object>>(mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.showToast("删除成功");
                start();
            }
        });
    }
}
