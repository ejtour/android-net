package com.hll_sc_app.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/11
 */

public class PrivacyDialog extends BaseDialog {
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

    public PrivacyDialog(@NonNull Activity context, IChangeListener listener) {
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
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(BuildConfig.isOdm ? 110 : 60);
        setCancelable(false);
    }

    private void initView() {
        if (BuildConfig.isOdm) {
            SpannableString ss = new SpannableString("你可阅读《隐私政策和用户协议》了解详细信息。如您同意，请点击\"同意\",开始接受我们的服务。");
            ss.setSpan(new Clickable(), 4, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mContent2.setText(ss);
        } else {
            mTitle.setText("二十二城供应商个人信息保护指引");
            mContent.setVisibility(View.GONE);
            String source = "感谢您选择二十二城供应商APP。\n我们非常重视您的个人信息和隐私安全。为了更好的保障您的个人利益，在您使用我们的产品前，" +
                    "请务必审慎阅读《二十二城供应商用户服务协议》与《二十二城供应商隐私权政策》内的全部内容，同意并接受全部条款后开始使用我们的产品和服务。" +
                    "我们深知个人信息对您的重要性，我们将严格遵守有关法律法规，并采取相应的重要保护技术措施，尽力保护您的个人信息安全。在使用APP的过程中，" +
                    "我们会基于您的授权，收集、使用您的个人信息，您有权拒绝或者取消授权。";
            String agreement = "《二十二城供应商用户服务协议》";
            String privacy = "《二十二城供应商隐私权政策》";
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new AgreementClickable(), source.indexOf(agreement), source.indexOf(agreement) + agreement.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new PrivacyClickable(), source.indexOf(privacy), source.indexOf(privacy) + privacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mContent2.setText(ss);
            mContent2.setMaxLines(12);
            mCancel.setText("放弃并退出");
            mCancel.setTextColor(ContextCompat.getColor(mCancel.getContext(), R.color.color_999999));
            mSure.setText("继续使用");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_privacy, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.dp_cancel)
    public void cancel() {
        dismiss();
        System.exit(0);
    }

    @OnClick(R.id.dp_sure)
    public void sure() {
        GlobalPreference.putParam(Constants.PRIVACY_KEY, true);
        dismiss();
        mListener.onChanged();
    }

    private static class Clickable extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("隐私政策和用户协议", "file:////android_asset/registerLegal.html");
        }
    }

    private static class PrivacyClickable extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("隐私权政策", "file:////android_asset/privacyPolicy.html");
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    private static class AgreementClickable extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("用户服务协议", "file:////android_asset/userAgreement.html");
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}
