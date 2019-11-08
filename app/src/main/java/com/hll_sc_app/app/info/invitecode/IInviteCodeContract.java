package com.hll_sc_app.app.info.invitecode;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.InviteCodeResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

public interface IInviteCodeContract {
    interface IInviteCodeView extends ILoadView {
        void setData(InviteCodeResp resp);
    }

    interface IInviteCodePresenter extends IPresenter<IInviteCodeView> {

    }
}
