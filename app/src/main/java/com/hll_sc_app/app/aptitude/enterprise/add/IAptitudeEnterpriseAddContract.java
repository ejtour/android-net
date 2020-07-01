package com.hll_sc_app.app.aptitude.enterprise.add;

import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.bean.aptitude.AptitudeEnterpriseBean;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

interface IAptitudeEnterpriseAddContract {
    interface IAptitudeEnterpriseAddView extends IImageUploadContract.IImageUploadView {
        void setData(List<AptitudeTypeBean> list);

        void success();
    }

    interface IAptitudeEnterpriseAddPresenter extends IImageUploadContract.IImageUploadPresenter {
        void save(AptitudeEnterpriseBean bean);
    }
}
