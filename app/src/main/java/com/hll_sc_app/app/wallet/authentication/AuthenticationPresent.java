package com.hll_sc_app.app.wallet.authentication;


import android.text.TextUtils;

import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AreaListReq;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.wallet.WalletInfoReq;
import com.hll_sc_app.rest.Upload;

import java.util.List;

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
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                })
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
                            mView.showError(e);
                        }
                    }
                });
    }

    @Override
    public void setWalletInfo() {
        WalletInfo info = ((IAuthenticationContract.IView) mView).getWalletInfo();
        if (TextUtils.isEmpty(info.getSettleUnitID())) {
            createAccount(info.getSettleUnitName());
            return;
        }
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseReq<WalletInfo> baseReq = new BaseReq<>();
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
        }else {
            info.setGroupName(userBean.getGroupName());
            if (TextUtils.isEmpty(info.getSettleUnitName())) {
                info.setSettleUnitName(userBean.getGroupName());
            }
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
    public void imageUpload(String path) {
        if (mView instanceof IAuthenticationContract.IView) {
            Upload.upload(mView, path, ((IAuthenticationContract.IView) mView)::uploadImgSuccess);
        }
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
                        // no-op
                    }
                });
    }

    @Override
    public void createAccount(String groupName) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        WalletService.INSTANCE
                .createSettlementObject(BaseMapReq.newBuilder()
                        .put("groupID", userBean.getGroupID())
                        .put("groupName", groupName)
                        .put("groupType", "1")
                        .create())
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
                            getWalletInfo();
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
}
