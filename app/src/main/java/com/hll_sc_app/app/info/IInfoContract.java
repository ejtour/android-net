package com.hll_sc_app.app.info;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.user.CertifyReq;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

public interface IInfoContract {
    interface IInfoView extends ILoadView {
        void setData(GroupInfoResp resp);
        void cacheUrl(String url);
        void avatarChanged();
    }

    interface IInfoPresenter extends IPresenter<IInfoView> {
        void reqCertify(CertifyReq req);

        void upload(File file);
    }
}
