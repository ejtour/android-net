package com.hll_sc_app.app.aptitude.info;

import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

interface IAptitudeInfoContract {
    interface IAptitudeInfoView extends IImageUploadContract.IImageUploadView {
        void setData(AptitudeInfoResp resp);

        void saveSuccess();
    }

    interface IAptitudeInfoPresenter extends IImageUploadContract.IImageUploadPresenter {
        void save(AptitudeInfoReq req);
    }
}
