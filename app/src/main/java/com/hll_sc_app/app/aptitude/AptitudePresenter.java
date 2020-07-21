package com.hll_sc_app.app.aptitude;

import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;
import com.hll_sc_app.rest.Upload;

import java.util.List;

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
        Aptitude.queryAptitudeList(mView.getProductID(), new SimpleObserver<SingleListResp<AptitudeBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<AptitudeBean> aptitudeEnterpriseBeanSingleListResp) {
                mView.setData(aptitudeEnterpriseBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void register(IImageUploadContract.IImageUploadView view) {
        mView = (IAptitudeContract.IAptitudeView) CommonUtils.requireNonNull(view);
    }

    @Override
    public void getTypeList() {
        Aptitude.queryAptitudeTypeList(mView.getType(), new SimpleObserver<List<AptitudeTypeBean>>(mView) {
            @Override
            public void onSuccess(List<AptitudeTypeBean> aptitudeTypeBeans) {
                mView.cacheTypeList(aptitudeTypeBeans);
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

    @Override
    public void upload(String path) {
        Upload.upload(mView, path, mView::setImageUrl);
    }
}
