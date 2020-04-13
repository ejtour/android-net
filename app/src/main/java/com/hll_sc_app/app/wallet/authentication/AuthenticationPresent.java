package com.hll_sc_app.app.wallet.authentication;


import android.app.Activity;
import android.text.TextUtils;

import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AreaListReq;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.wallet.WalletInfoReq;
import com.hll_sc_app.rest.Upload;

import java.io.File;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 实名认证 主页面
 *
 * @author zc
 */
public class AuthenticationPresent implements IAuthenticationContract.IPresent {
    private ILoadView mView;

    static AuthenticationPresent newInstance() {
        return new AuthenticationPresent();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(ILoadView view) {
        mView = view;
    }

    @Override
    public void getAddress(AreaListReq areaListReq) {
        BaseReq<AreaListReq> baseReq = new BaseReq<>();
        baseReq.setData(areaListReq);
        WalletService.INSTANCE
                .queryAreaList(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .subscribe(new BaseCallback<List<AreaInfo>>() {
                    @Override
                    public void onSuccess(List<AreaInfo> result) {
                        if (mView.isActive()) {
                            ((IAuthenticationContract.IBaseInfoFragment) mView).getAddressSuccess(result);
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (mView.isActive()) {
                            ((IAuthenticationContract.IBaseInfoFragment) mView).getAddressFail();
                        }
                    }
                });

    }

    @Override
    public void getWalletInfo() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        WalletInfoReq walletInfoReq = new WalletInfoReq();
        walletInfoReq.setGroupID(userBean.getGroupID());
        BaseReq<WalletInfoReq> baseReq = new BaseReq<>();
        baseReq.setData(walletInfoReq);
        WalletService.INSTANCE
                .getWalletInfo(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .subscribe(new BaseCallback<WalletInfo>() {
                    @Override
                    public void onSuccess(WalletInfo result) {
                        if (mView.isActive()) {
                            ((IAuthenticationContract.IView) mView).getWalletInfoSuccess(result);
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (mView.isActive()) {
                            ((IAuthenticationContract.IView) mView).showError(e);
                        }
                    }
                });
    }

    @Override
    public void setWalletInfo() {
        BaseReq<WalletInfo> baseReq = new BaseReq<>();
        WalletInfo info = ((IAuthenticationContract.IView) mView).getWalletInfo();
        info.setCustomerServiceTel(info.getLpPhone());
        if (info.getUnitType() == 4) {
            info.setLpCardType(0);//小微模式，则为身份证
            info.setBankPersonType(2);
            info.setReceiverType(1);
            info.setLicenseBeginDate("0");
            info.setLicensePeriod("0");
            info.setBusiPermissionBeginDate("");
            info.setBusiPermissionEndDate("");
            info.setImgLicense("");
            info.setGroupName(info.getSettleUnitName());
            info.setBusiScope("");
            info.setImgProxyContract("");
            info.setProxyProtocol("");
            info.setLicenseAddress("");
            info.setLicenseCode("");
            info.setImgBusiCounter("");
            info.setContactIDCardNo("");
            info.setLpPhone(info.getOperatorMobile());
            info.setImgBusiPermission("");
            info.setImgBankLicense("");
            info.setOperatorName(info.getLpName());
        }
        baseReq.setData(info);
        WalletService.INSTANCE
                .submitAuthenInfo(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                })
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        if (mView.isActive()) {
                            ((IAuthenticationContract.IView) mView).setWalletInfoSuccess();
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (mView.isActive()) {
                            mView.showError(e);
                        }
                    }
                });
    }

    @Override
    public void imageUpload(File imageFile) {
        Luban.with((Activity) mView)
                .load(imageFile)
                .ignoreBy(2000)//2M
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) );
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI

                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        Upload.upload((BaseLoadActivity)mView,file.getAbsolutePath(), filepath -> {
                            ((IAuthenticationContract.IView) mView).uploadImgSuccess(filepath);
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }


    @Override
    public void ocrImage(int lpCardType, String imageUrl) {
        WalletService.INSTANCE
                .ocrImage(BaseMapReq.newBuilder()
                        .put("lpCardType", String.valueOf(lpCardType))
                        .put("imageUrl", "http://res.hualala.com/" + imageUrl)
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                })
                .subscribe(new BaseCallback<OcrImageResp>() {
                    @Override
                    public void onSuccess(OcrImageResp resp) {
                        if (mView.isActive()) {
                            ((IAuthenticationContract.IView) mView).orcImageSuccess(resp);
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (mView.isActive()) {
//                            mView.showError(e);
                        }
                    }
                });
    }
}
