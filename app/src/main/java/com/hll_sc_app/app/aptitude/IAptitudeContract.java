package com.hll_sc_app.app.aptitude;

import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;

import java.util.List;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/6/29
 */

public interface IAptitudeContract {
    interface IAptitudeView extends IImageUploadContract.IImageUploadView {
        void setData(List<AptitudeBean> list);

        void cacheTypeList(List<AptitudeTypeBean> list);

        int getType();

        void saveSuccess();

        default String getProductID() {
            return null;
        }

        default String getExtGroupID() {
            return null;
        }
    }

    interface IAptitudePresenter extends IImageUploadContract.IImageUploadPresenter {
        void getTypeList();

        void save(AptitudeReq list);
    }
}
