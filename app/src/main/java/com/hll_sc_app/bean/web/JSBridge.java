package com.hll_sc_app.bean.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.widget.ShareDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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

    @JavascriptInterface
    public void nativeJson(String json) {
        Activity activity = mLoadView.get();
        if (activity == null) return;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String type = jsonObject.optString("type");
            if ("share".equals(type)) {
                JSONObject data = jsonObject.optJSONObject("data");
                if (data == null) return;
                ShareDialog.ShareParam param = ShareDialog.ShareParam.createWebShareParam(
                        "好物值得分享",
                        "http://res.hualala.com/" + data.optString("imageUrl"),
                        data.optString("name"),
                        BuildConfig.ODM_NAME + "的生鲜食材很棒棒呦，快来看看吧~",
                        data.optString("url")
                ).setAppendPostfix(true);
                EventBus.getDefault().post(param);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
