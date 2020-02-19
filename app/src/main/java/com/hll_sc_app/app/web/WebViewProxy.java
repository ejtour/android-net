package com.hll_sc_app.app.web;

import android.annotation.SuppressLint;
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

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.web.JSBridge;
import com.hll_sc_app.utils.Constants;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class WebViewProxy {
    private Bundle mArgs;
    private FrameLayout mWebViewContainer;
    private WebView mWebView;

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
    public void initWebView(@Nullable ILoadView view, WebChromeClient chromeClient, WebViewClient webViewClient, String bridgeName) {
        mWebView = new WebView(mWebViewContainer.getContext().getApplicationContext());
        mWebViewContainer.addView(mWebView, 0);
        if (chromeClient != null)
            mWebView.setWebChromeClient(chromeClient);
        if (webViewClient != null)
            mWebView.setWebViewClient(webViewClient);
        mWebView.setBackgroundColor(0);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (view != null)
            mWebView.addJavascriptInterface(new JSBridge(view), mArgs.getString(Constants.WEB_JS_NAME, bridgeName));

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
