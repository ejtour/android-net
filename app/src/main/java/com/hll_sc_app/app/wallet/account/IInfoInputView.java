package com.hll_sc_app.app.wallet.account;

import com.hll_sc_app.bean.wallet.AuthInfo;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public interface IInfoInputView {
    void initData(AuthInfo info);

    void setImageUrl(String url);

    String getTitle();
}
