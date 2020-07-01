package com.hll_sc_app.app.aptitude.info;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.bean.groupInfo.GroupInfoReq;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;
import com.hll_sc_app.rest.Upload;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

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
                // no-op
            }
        });
    }

    @Override
    public void loadInfo() {
        Aptitude.queryBaseInfo(new SimpleObserver<AptitudeInfoResp>(mView) {
            @Override
            public void onSuccess(AptitudeInfoResp resp) {
                mView.setData(resp);
            }
        });
    }

    @Override
    public void start() {
        SimpleObserver<GroupInfoResp> observer = new SimpleObserver<GroupInfoResp>(mView) {
            @Override
            public void onSuccess(GroupInfoResp groupInfoResp) {
                mView.setLicenseUrl(groupInfoResp.getLicencePhotoUrl());
                mView.selectType(CommonUtils.getInt(groupInfoResp.getCompanyType()));
                loadInfo();
            }
        };
        GroupInfoReq req = new GroupInfoReq();
        req.setGroupID(UserConfig.getGroupID());
        UserService.INSTANCE.getGroupInfo(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IImageUploadContract.IImageUploadView view) {
        mView = ((IAptitudeInfoContract.IAptitudeInfoView) CommonUtils.requireNonNull(view));
    }

    @Override
    public void upload(String path) {
        Upload.upload(mView, path, mView::setImageUrl);
    }
}
