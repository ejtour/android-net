package com.hll_sc_app.app.info.invitecode;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.user.InviteCodeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

public class InviteCodePresenter implements IInviteCodeContract.IInviteCodePresenter {
    private IInviteCodeContract.IInviteCodeView mView;

    @Override
    public void start() {
        User.queryInviteCode(new SimpleObserver<InviteCodeResp>(mView) {
            @Override
            public void onSuccess(InviteCodeResp inviteCodeResp) {
                mView.setData(inviteCodeResp);
            }
        });
    }

    @Override
    public void register(IInviteCodeContract.IInviteCodeView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
