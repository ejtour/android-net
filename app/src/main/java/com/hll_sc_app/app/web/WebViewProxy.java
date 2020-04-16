package com.hll_sc_app.app.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.web.JSBridge;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.hll_sc_app.utils.Constants;
import com.zhihu.matisse.Matisse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class WebViewProxy {
    private Bundle mArgs;
    private FrameLayout mWebViewContainer;
    private WebView mWebView;
    private Activity mActivity;

    public WebViewProxy(@NonNull Bundle args, @NonNull FrameLayout webViewContainer) {
        mArgs = args;
        mWebViewContainer = webViewContainer;
    }

    public void initWebView() {
        initWebView(null, null);
    }

    public void initWebView(WebChromeClient chromeClient, WebViewClient webViewClient) {
        initWebView(null, chromeClient, webViewClient, null);
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    public void initWebView(@Nullable Activity activity, WebChromeClient chromeClient, WebViewClient webViewClient, String bridgeName) {
        mWebView = new WebView(mWebViewContainer.getContext().getApplicationContext());
        mWebViewContainer.addView(mWebView, 0);
        if (chromeClient != null)
            mWebView.setWebChromeClient(chromeClient);
        if (webViewClient != null)
            mWebView.setWebViewClient(webViewClient);
        mWebView.setBackgroundColor(0);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (activity != null)
            mWebView.addJavascriptInterface(new JSBridge(activity), mArgs.getString(Constants.WEB_JS_NAME, bridgeName));
        mActivity = activity;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(false);
        boolean zoom = mArgs.getBoolean(Constants.WEB_ZOOM, false);
        settings.setSupportZoom(zoom);
        settings.setBuiltInZoomControls(zoom);
        settings.setAllowFileAccessFromFileURLs(true);
        String url = mArgs.getString(Constants.WEB_URL);
        if (!TextUtils.isEmpty(url)) {
            loadUrl(url);
        } else {
            loadData(mArgs.getString(Constants.WEB_DATA), "text/html", "UTF-8");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == Constants.IMG_SELECT_REQ_CODE) {
            List<String> paths = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(paths) && mWebViewContainer.getContext() instanceof ILoadView && mActivity != null) {
                Upload.upload((BaseLoadActivity) mActivity, paths.get(0), filepath -> {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("type", "upLoadImage");
                        jsonObject.put("data", filepath);
                        mWebView.loadUrl("javascript:resolveMsgData('" + jsonObject.toString() + "')");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void destroy() {
        mWebViewContainer.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }

    public void goBack() {
        mWebView.goBack();
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void clearHistory() {
        mWebView.clearHistory();
    }

    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        mWebView.loadUrl(url);
    }

    public void loadData(String data, String mimeType, String encoding) {
        if (TextUtils.isEmpty(data)) return;
        mWebView.loadData(data, mimeType, encoding);
    }
}
