package com.hll_sc_app.app.wallet.account.create;

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

public class CreateAccountPresenter implements ICreateAccountContract.ICreateAccountPresenter {
    private ICreateAccountContract.ICreateAccountView mView;

    public static CreateAccountPresenter newInstance() {
        return new CreateAccountPresenter();
    }

    private CreateAccountPresenter() {
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
    public void queryAreaList(int areaType,String areaParentId) {
        Wallet.queryAreaList(areaType, areaParentId, new SimpleObserver<List<AreaInfo>>(mView) {
            @Override
            public void onSuccess(List<AreaInfo> areaInfos) {
                mView.handleAreaList(areaInfos);
            }
        });
    }

    @Override
    public void createAccount(AuthInfo info) {
        Wallet.createAccount(info, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.createSuccess();
            }
        });
    }

    @Override
    public void register(ICreateAccountContract.ICreateAccountView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
