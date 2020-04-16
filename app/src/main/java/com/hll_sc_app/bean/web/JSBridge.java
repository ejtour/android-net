package com.hll_sc_app.bean.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.utils.UserConfig;

import java.lang.ref.WeakReference;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class JSBridge {
    private WeakReference<Activity> mLoadView;

    public JSBridge(Activity activity) {
        mLoadView = new WeakReference<>(activity);
    }

    @JavascriptInterface
    public void showLoading(boolean isLoading) {
        Activity activity = mLoadView.get();
        if (activity == null) return;
        if (activity instanceof ILoadView) {
            if (isLoading) {
                ((ILoadView) activity).showLoading();
            } else {
                ((ILoadView) activity).hideLoading();
            }
        }
    }

    @JavascriptInterface
    public void nativeEvent(String type) {
        Activity activity = mLoadView.get();
        if (activity == null) return;
        switch (type) {
            case "rootNavMessage":
                activity.finish();
                break;
            case "tokenFailure":
                UserConfig.reLogin();
                break;
        }
    }
}
