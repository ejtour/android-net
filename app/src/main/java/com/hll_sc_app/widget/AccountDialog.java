package com.hll_sc_app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountDialog extends BaseDialog {
    private final IChangeListener mListener;
    @BindView(R.id.dp_title)
    TextView mTitle;
    @BindView(R.id.dp_content)
    TextView mContent;
    @BindView(R.id.dp_content_2)
    LeakFixedTextView mContent2;
    @BindView(R.id.dp_div)
    View mDiv;
    @BindView(R.id.dp_cancel)
    TextView mCancel;
    @BindView(R.id.dp_sure)
    TextView mSure;

    public AccountDialog(@NonNull Activity context, IChangeListener listener) {
        super(context);
        initView();
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    private void initWindow() {
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(60);
        setCancelable(false);
    }

    private void initView() {

        mTitle.setText("");
        mContent.setVisibility(View.GONE);
        String source = "注销后再次注册，该账号信息数据无法恢复，请慎重操作";
        SpannableString ss = new SpannableString(source);
        mContent2.setText(ss);
        mContent2.setMaxLines(12);
        mCancel.setText("终止注销");
        mCancel.setTextColor(ContextCompat.getColor(mCancel.getContext(), R.color.color_999999));
        mSure.setText("确认注销");

    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_account, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.dp_cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.dp_sure)
    public void sure() {

        dismiss();
        mListener.onChanged();
    }

}
