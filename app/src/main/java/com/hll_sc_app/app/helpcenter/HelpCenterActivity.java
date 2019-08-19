package com.hll_sc_app.app.helpcenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.push.common.util.JSONUtils;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.JsonObject;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 帮助中心 WebView
 *
 * @author zc
 */
@Route(path = RouterConfig.ACTIVITY_HELP_CENTER, extras = Constant.LOGIN_EXTRA)
public class HelpCenterActivity extends BaseLoadActivity {
    @BindView(R.id.asd_header)
    TitleBar mHeaderBar;
    @BindView(R.id.webview_container)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgressBar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        mWebView = null;
        unbinder.unbind();
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        //标题栏
        StatusBarCompat.setStatusBarColor(this, 0xFFFFFFFF);
        //头部绑定事件
        hearbarBindEvent();
        //！这个很有必要 不加 点击则会跳到浏览器内去显示 而不是在app中
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //不使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //！设置适应Html5 不写这个不显示
        webSettings.setDomStorageEnabled(true);
        //加载请求帮助中心html页面
        String params = Base64.encodeToString(JsonUtil.toJson(new HelpCenterJsParams()).getBytes(), Base64.DEFAULT);
        mWebView.loadUrl(HttpConfig.getHelpCenterHost() + "/?sourceData=" + params);

    }

    /**
     * 头部组件绑定返回事件
     */
    private void hearbarBindEvent() {
        mHeaderBar.setLeftBtnClick(v -> {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        });
    }

    /**
     * webview
     *
     * @return
     */
    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
