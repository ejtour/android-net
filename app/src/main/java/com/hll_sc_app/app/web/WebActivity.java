package com.hll_sc_app.app.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ShareDialog;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

@Route(path = RouterConfig.WEB, extras = Constant.AUTH_PROCESS)
public class WebActivity extends BaseLoadActivity {

    @Autowired(name = "bundle")
    Bundle mBundle;
    @BindView(R.id.aw_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aw_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.aw_web_view_container)
    FrameLayout mWebViewContainer;
    private WebViewProxy mProxy;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ShareDialog mShareDialog;

    public static void start(String title, String url) {
        start(title, url, null, null, false);
    }

    /**
     * @param title      网页标题，为null时不显示标题栏，内容延申至状态栏
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

    public static void start(String title, String url, String bridgeName) {
        start(title, url, bridgeName, null, false);
    }

    public static void start(String title, String url, boolean zoom) {
        start(title, url, null, null, zoom);
    }

    public static void start(String title, String url, String bridgeName, boolean zoom) {
        start(title, url, bridgeName, null, zoom);
    }

    public static void startWithData(String title, String data) {
        Bundle args = new Bundle();
        args.putString(Constants.WEB_TITLE, title);
        args.putString(Constants.WEB_DATA, data);
        RouterUtil.goToActivity(RouterConfig.WEB, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        String title = mBundle.getString(Constants.WEB_TITLE);
        if (title == null) {
            mTitleBar.setVisibility(View.GONE);
        } else {
            mTitleBar.setHeaderTitle(title);
            mTitleBar.setLeftBtnClick(v -> onBackPressed());
        }
        initWebView();
    }

    @Override
    protected void initSystemBar() {
        String title = mBundle.getString(Constants.WEB_TITLE);
        if (title == null) {
            StatusBarUtil.setTranslucent(this, true);
        } else {
            super.initSystemBar();
        }
    }

    private void initWebView() {
        mProxy = new WebViewProxy(mBundle, mWebViewContainer);
        mProxy.initWebView(this, new WebChromeClient() {
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

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;
                UIUtils.selectPhoto(WebActivity.this);
                return true;
            }
        }, new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String js = mBundle.getString(Constants.WEB_JS_FUNCTION);
                if (!TextUtils.isEmpty(js)) {
                    view.post(() -> view.loadUrl(String.format("javascript:%s", js)));
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    UIUtils.callPhone(url);
                    return true;
                } else if (url.startsWith("mailto:")) {
                    UIUtils.sendMail(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        }, "JSBridge");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShareDialog != null) {
            mShareDialog.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<Uri> paths = Matisse.obtainResult(data);
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(paths.toArray(new Uri[]{}));
                mFilePathCallback = null;
            }
        } else if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (mShareDialog != null) {
            mShareDialog.release();
        }
        EventBus.getDefault().unregister(this);
        mProxy.destroy();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleShareEvent(ShareDialog.ShareParam param) {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this);
        }
        mShareDialog.setData(param);
        mShareDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mProxy.canGoBack()) {
            mProxy.goBack();
            return;
        }
        super.onBackPressed();
    }
}
