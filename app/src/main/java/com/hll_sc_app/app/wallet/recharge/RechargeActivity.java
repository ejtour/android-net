package com.hll_sc_app.app.wallet.recharge;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.web.WebViewProxy;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ClearEditText;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.wallet.RechargeDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

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
    @BindView(R.id.awr_web_view_container)
    FrameLayout mWebViewContainer;
    private WebViewProxy mProxy;
    @BindView(R.id.awr_progress_bar)
    ProgressBar mProgressBar;
    private RechargeDialog mDialog;
    private IRechargeContract.IRechargePresenter mPresenter;

    public static void start(Activity activity, String settleUnitID) {
        Object[] args = {settleUnitID};
        RouterUtil.goToActivity(RouterConfig.WALLET_RECHARGE, activity, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_wallet_recharge);
        ButterKnife.bind(this);
        initWebView();
        mPresenter = RechargePresenter.newInstance(mSettleUnitID);
        mPresenter.register(this);
    }

    private void initWebView() {
        mProxy = new WebViewProxy(new Bundle(), mWebViewContainer);
        mProxy.initWebView(new WebChromeClient() {
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
        }, new WebViewClient() {
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
        mProxy.destroy();
        super.onDestroy();
    }

    @Override
    public void rechargeSuccess(RechargeResp rechargeResp) {
        mDialog.dismiss();
        mWebViewContainer.setVisibility(View.VISIBLE);
        mProxy.loadData(createWebForm(rechargeResp.getPreRechargeInfo()), "text/html", "UTF-8");
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
        Utils.processMoney(s, false);
        mNextStep.setEnabled(!TextUtils.isEmpty(s) && Double.parseDouble(s.toString()) > 0);
    }

    @OnEditorAction(R.id.awr_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT && mNextStep.isEnabled())
            recharge();
        return true;
    }
}
