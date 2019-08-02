package com.hll_sc_app.app.wallet.account;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Wallet;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class AccountPresenter implements IAccountContract.IAccountPresenter {
    protected IAccountContract.IAccountView mView;

    protected AccountPresenter() {
    }

    public static AccountPresenter newInstance() {
        return new AccountPresenter();
    }

    @Override
    public void start() {
        Wallet.queryAuthInfo(new SimpleObserver<AuthInfo>(mView) {
            @Override
            public void onSuccess(AuthInfo info) {
                mView.handleAuthInfo(info);
            }
        });
    }

    @Override
    public void register(IAccountContract.IAccountView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void queryAreaList(int areaType, String areaParentId) {
        Wallet.queryAreaList(areaType, areaParentId, new SimpleObserver<List<AreaInfo>>(mView) {
            @Override
            public void onSuccess(List<AreaInfo> areaInfos) {
                mView.handleAreaList(areaInfos);
            }
        });
    }

    @Override
    public void commitAuthInfo(AuthInfo info) {
        Wallet.createAccount(info, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.commitSuccess();
            }
        });
    }
}
