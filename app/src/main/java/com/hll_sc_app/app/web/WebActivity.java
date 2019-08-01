package com.hll_sc_app.app.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.web.JSBridge;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

@Route(path = RouterConfig.WEB)
public class WebActivity extends BaseLoadActivity {

    /**
     * @param title      网页标题
     * @param url        地址
     * @param bridgeName js 调用 android 方法用的名字，默认为 JSBridge
     * @param jsFunction 页面加载后调用的初始化方法 例如需要由android 调用 js 的 init(name) 方法，那么传 "init(name)"
     * @param zoom       是否支持网页缩放
     */
    public static void start(String title, String url, String bridgeName, String jsFunction, boolean zoom) {
        Bundle args = new Bundle();
        args.putString(Constants.WEB_TITLE, title);
        args.putString(Constants.WEB_URL, url);
        args.putString(Constants.WEB_JS_NAME, bridgeName);
        args.putString(Constants.WEB_JS_FUNCTION, jsFunction);
        args.putBoolean(Constants.WEB_ZOOM, zoom);
        RouterUtil.goToActivity(RouterConfig.WEB, args);
    }

    public static void start(String title, String url) {
        start(title, url, null, null, false);
    }

    public static void start(String title, String url, String bridgeName) {
        start(title, url, bridgeName, null, false);
    }

    public static void start(String title, String url, boolean zoom) {
        start(title, url, null, null, zoom);
    }

    public static void start(String title, String url, String bridgeName, boolean zoom) {
        start(title, url, bridgeName, null, zoom);
    }

    @Autowired(name = "bundle")
    Bundle mBundle;
    @BindView(R.id.aw_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aw_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.aw_web_view_container)
    FrameLayout mWebViewContainer;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mBundle.getString(Constants.WEB_TITLE));
        initWebView();
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebView() {
        mWebView = new WebView(getApplicationContext());
        mWebViewContainer.addView(mWebView, 0);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String js = mBundle.getString(Constants.WEB_JS_FUNCTION);
                if (!TextUtils.isEmpty(js)) {
                    view.post(() -> view.loadUrl(String.format("javascript:%s", js)));
                }
            }
        });
        mWebView.setBackgroundColor(0);
        mWebView.addJavascriptInterface(new JSBridge(this), mBundle.getString(Constants.WEB_JS_NAME, "JSBridge"));

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(false);
        boolean zoom = mBundle.getBoolean(Constants.WEB_ZOOM, false);
        settings.setSupportZoom(zoom);
        settings.setBuiltInZoomControls(zoom);
        settings.setAllowFileAccessFromFileURLs(true);
        mWebView.loadUrl(mBundle.getString(Constants.WEB_URL));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mWebViewContainer.removeAllViews();
        mWebView.destroy();
        mWebView = null;
        super.onDestroy();
    }
}
