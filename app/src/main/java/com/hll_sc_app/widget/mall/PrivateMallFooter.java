package com.hll_sc_app.widget.mall;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */

public class PrivateMallFooter extends ConstraintLayout {
    @BindView(R.id.pmf_step_1)
    TextView mStep1;
    @BindView(R.id.pmf_step_2)
    TextView mStep2;
    @BindView(R.id.pmf_step_3)
    TextView mStep3;
    @BindView(R.id.pmf_step_4)
    TextView mStep4;

    public PrivateMallFooter(Context context) {
        this(context, null);
    }

    public PrivateMallFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrivateMallFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.base_bg_white_radius_5_solid);
        View view = View.inflate(context, R.layout.view_private_mall_footer, this);
        ButterKnife.bind(this, view);
        String source = mStep1.getText().toString();
        int color = ContextCompat.getColor(context, R.color.color_222222);
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(color), source.lastIndexOf("台") + 1, source.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStep1.setText(spannableString);

        source = mStep2.getText().toString();
        spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(color), source.lastIndexOf("为") + 1, source.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStep2.setText(spannableString);

        source = mStep3.getText().toString();
        spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(color), source.indexOf("“") + 1, source.indexOf("”"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStep3.setText(spannableString);

        source = mStep4.getText().toString();
        spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(color), source.indexOf("管"), source.indexOf("扫"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStep4.setText(spannableString);
    }
}
