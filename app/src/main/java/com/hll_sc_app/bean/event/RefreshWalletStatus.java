package com.hll_sc_app.bean.event;

/**
 * 更新钱包的状态 刷新
 *
 * @author zc
 */
public class RefreshWalletStatus {
    public RefreshWalletStatus(boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
    }

    private boolean isShowLoading;

    public boolean isShowLoading() {
        return isShowLoading;
    }

    public void setShowLoading(boolean showLoading) {
        isShowLoading = showLoading;
    }
}
