package com.hll_sc_app.app.wallet.account.auth;

import com.hll_sc_app.app.wallet.account.AccountPresenter;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.rest.Upload;
import com.hll_sc_app.rest.Wallet;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class AuthAccountPresenter extends AccountPresenter {

    public static AuthAccountPresenter newInstance() {
        return new AuthAccountPresenter();
    }

    @Override
    public void commitAuthInfo(AuthInfo info) {
        Wallet.authAccount(info, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.commitSuccess();
            }
        });
    }

    @Override
    public void imageUpload(File file) {
        Upload.imageUpload(file, new SimpleObserver<String>(mView) {
            @Override
            public void onSuccess(String s) {
                mView.showImage(s);
            }
        });
    }
}
