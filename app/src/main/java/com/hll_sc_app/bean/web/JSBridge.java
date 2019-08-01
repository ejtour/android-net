package com.hll_sc_app.bean.web;

import android.webkit.JavascriptInterface;

import com.hll_sc_app.base.ILoadView;

import java.lang.ref.WeakReference;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class JSBridge {
    private WeakReference<ILoadView> mLoadView;

    public JSBridge(ILoadView loadView) {
        mLoadView = new WeakReference<>(loadView);
    }

    @JavascriptInterface
    public void showLoading(boolean isLoading) {
        if (mLoadView.get() == null) return;
        if (isLoading) {
            mLoadView.get().showLoading();
        } else {
            mLoadView.get().hideLoading();
        }
    }
}
