package com.hll_sc_app.app.aptitude.enterprise.add;

import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeEnterpriseBean;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;
import com.hll_sc_app.rest.Upload;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

class AptitudeEnterpriseAddPresenter implements IAptitudeEnterpriseAddContract.IAptitudeEnterpriseAddPresenter {
    private IAptitudeEnterpriseAddContract.IAptitudeEnterpriseAddView mView;

    private AptitudeEnterpriseAddPresenter() {
    }

    public static AptitudeEnterpriseAddPresenter newInstance() {
        return new AptitudeEnterpriseAddPresenter();
    }

    @Override
    public void save(AptitudeEnterpriseBean bean) {
        Aptitude.editEnterpriseInfo(bean, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.success();
            }
        });
    }

    @Override
    public void upload(String path) {
        Upload.upload(mView, path, mView::setImageUrl);
    }

    @Override
    public void start() {
        Aptitude.queryAptitudeTypeList(1, new SimpleObserver<List<AptitudeTypeBean>>(mView) {
            @Override
            public void onSuccess(List<AptitudeTypeBean> aptitudeTypeBeans) {
                mView.setData(aptitudeTypeBeans);
            }
        });
    }

    @Override
    public void register(IImageUploadContract.IImageUploadView view) {
        mView = ((IAptitudeEnterpriseAddContract.IAptitudeEnterpriseAddView) CommonUtils.requireNonNull(view));
    }
}
