package com.hll_sc_app.app.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hll_sc_app.bean.web.JSBridge;
import com.hll_sc_app.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;

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
    public void initWebView(@Nullable Activity activity, WebChromeClient chromeClient, WebViewClient webViewClient, String bridgeName) {
        mWebViewContainer.setLayerType(View.LAYER_TYPE_HARDWARE, null);
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

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(false);
        boolean zoom = mArgs.getBoolean(Constants.WEB_ZOOM, false);
        settings.setSupportZoom(zoom);
        settings.setBuiltInZoomControls(zoom);
        settings.setAllowFileAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String url = mArgs.getString(Constants.WEB_URL);
        if (!TextUtils.isEmpty(url)) {
            loadUrl(url);
        } else {
            loadData(mArgs.getString(Constants.WEB_DATA), "text/html", "UTF-8");
        }
    }

    public void save() {
        mWebView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mWebView.layout(0, 0, mWebView.getMeasuredWidth(), mWebView.getMeasuredHeight());
        mWebView.setDrawingCacheEnabled(true);
        mWebView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
                mWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        mWebView.draw(canvas);
        try {
            File file = new File(mWebViewContainer.getContext().getExternalCacheDir() + "print.png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
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
