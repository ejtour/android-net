package com.hll_sc_app.app.wallet.create;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;


/**
 * 协议窗口
 * @author zc
 */
public class ProtocolDialog extends BaseDialog {
    private WebView mWebView;
    private TextView mRefuse;
    private TextView mAgree;

    public ProtocolDialog(@NonNull Activity context) {
        super(context);
    }


    public ProtocolDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
    }

    public ProtocolDialog(@NonNull Activity context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View mRootView = inflater.inflate(R.layout.dialog_wallet_open_protocol, null);
        mWebView = mRootView.findViewById(R.id.webview_container);
        mRefuse = mRootView.findViewById(R.id.txt_refuse);
        mAgree = mRootView.findViewById(R.id.txt_agree);
        initView();
        return mRootView;
    }

    private void initView() {
        mRefuse.setOnClickListener(v -> {
            dismiss();
            mActivity.finish();
        });
        mAgree.setOnClickListener(v -> {
            dismiss();
        });
        mWebView.loadUrl("file:///android_asset/walletProtocol.html");


    }

    @Override
    public void onBackPressed() {
        return;
    }
}
