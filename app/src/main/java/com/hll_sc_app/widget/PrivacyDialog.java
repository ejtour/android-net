package com.hll_sc_app.widget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.utils.Constants;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/11
 */

public class PrivacyDialog extends BaseDialog {
    private final IChangeListener mListener;

    public PrivacyDialog(@NonNull Activity context, IChangeListener listener) {
        super(context);
        setCancelable(false);
        initView();
        mListener = listener;
    }

    private void initView() {
        TextView content = mRootView.findViewById(R.id.dp_content_2);
        if (BuildConfig.isOdm) {
            SpannableString ss = new SpannableString("你可阅读《隐私政策和用户协议》了解详细信息。如您同意，请点击\"同意\",开始接受我们的服务。");
            ss.setSpan(new Clickable(), 4, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setText(ss);
        } else {
            SpannableString ss = new SpannableString("你可阅读《隐私权政策》和《用户服务协议》了解详细信息。如您同意，请点击\"同意\",开始接受我们的服务。");
            ss.setSpan(new PrivacyClickable(), 4, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AgreementClickable(), 12, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setText(ss);
        }
        mRootView.findViewById(R.id.dp_sure).setOnClickListener(v -> {
            GlobalPreference.putParam(Constants.PRIVACY_KEY, true);
            dismiss();
            mListener.onChanged();
        });
        mRootView.findViewById(R.id.dp_cancel).setOnClickListener(v -> {
            dismiss();
            System.exit(0);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_privacy, null);
    }

    private static class Clickable extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("隐私政策和用户协议", "file:////android_asset/registerLegal.html");
        }
    }

    private static class PrivacyClickable extends ClickableSpan{
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("隐私权政策", "file:////android_asset/privacyPolicy.html");
        }
    }

    private static class AgreementClickable extends ClickableSpan{
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("用户服务协议", "file:////android_asset/userAgreement.html");
        }
    }
}
