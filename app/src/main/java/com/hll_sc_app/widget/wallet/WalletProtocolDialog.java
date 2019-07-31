package com.hll_sc_app.widget.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class WalletProtocolDialog extends BaseDialog {
    WebView mWebView;
    @BindView(R.id.dwp_reject)
    TextView mReject;
    @BindView(R.id.dwp_web_view_container)
    FrameLayout mWebViewContainer;

    public WalletProtocolDialog(@NonNull Activity context, View.OnClickListener rejectListener) {
        super(context);
        mReject.setOnClickListener(rejectListener);
        setOnDismissListener(dialog -> release());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_wallet_protocol, null);
        ButterKnife.bind(this, view);
        initWebView();
        mWebView.loadUrl("file:///android_asset/walletProtocol.html");
        return view;
    }

    private void initWebView() {
        mWebView = new WebView(getContext().getApplicationContext());
        mWebViewContainer.addView(mWebView);
    }

    private void initWindow() {
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getWindow().getAttributes().width = UIUtils.dip2px(335);
        getWindow().getAttributes().height = UIUtils.dip2px(500);
        setCancelable(false);
    }

    @OnClick(R.id.dwp_agree)
    public void agree() {
        dismiss();
    }

    private void release() {
        mWebViewContainer.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
