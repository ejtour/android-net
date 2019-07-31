package com.hll_sc_app.app.wallet.recharge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ClearEditText;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.wallet.RechargeDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/29
 */
@Route(path = RouterConfig.WALLET_RECHARGE)
public class RechargeActivity extends BaseLoadActivity implements IRechargeContract.IRechargeView {
    private final String FINISH_TAG = "22cityRecharge";
    public static final int REQ_CODE = 0x354;
    @Autowired(name = "object0")
    String mSettleUnitID;
    @BindView(R.id.awr_edit)
    ClearEditText mEdit;
    @BindView(R.id.awr_next_step)
    TextView mNextStep;
    @BindView(R.id.awr_web_view)
    WebView mWebView;
    @BindView(R.id.awr_progress_bar)
    ProgressBar mProgressBar;
    private RechargeDialog mDialog;
    private IRechargeContract.IRechargePresenter mPresenter;
    private Unbinder unbinder;

    public static void start(Activity activity, String settleUnitID) {
        Object[] args = {settleUnitID};
        RouterUtil.goToActivity(RouterConfig.WALLET_RECHARGE, activity, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_wallet_recharge);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initWebView();
        mPresenter = RechargePresenter.newInstance(mSettleUnitID);
        mPresenter.register(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
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
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.contains(FINISH_TAG)) {
                    setResult(RESULT_OK);
                    finish();
                }
                return super.shouldInterceptRequest(view, url);
            }
        });
    }

    @Override
    protected void onDestroy() {
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        mWebView.destroy();
        mWebView = null;
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void rechargeSuccess(RechargeResp rechargeResp) {
        mDialog.dismiss();
        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadData(createWebForm(rechargeResp.getPreRechargeInfo()), "text/html", "UTF-8");
    }

    private String createWebForm(String content) {
        return "<html>" +
                "<body>" +
                content +
                "</body>" +
                "</html>" +
                "<script language=\"javascript\" type=\"text/javascript\">document.getElementById(\"rechargeSubmit\").submit();</script>";
    }

    @OnClick(R.id.awr_next_step)
    public void recharge() {
        String moneyContent = mEdit.getText().toString();
        showRechargeDialog(Double.parseDouble(moneyContent));
    }

    private void showRechargeDialog(double money) {
        if (mDialog == null)
            mDialog = new RechargeDialog(this, v -> mPresenter.recharge(Double.valueOf(v.getTag().toString())));
        mDialog.show(money);
    }

    @OnTextChanged(value = R.id.awr_edit, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable s) {
        if (s.toString().startsWith(".")) s.insert(0, "0");
        if (!CommonUtils.checkMoenyNum(s.toString()) && s.length() > 1)
            s.delete(s.length() - 1, s.length());
        mNextStep.setEnabled(!TextUtils.isEmpty(s) && Double.parseDouble(s.toString()) > 0);
    }

    @OnEditorAction(R.id.awr_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT && mNextStep.isEnabled())
            recharge();
        return true;
    }
}
