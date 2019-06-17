package com.hll_sc_app.base.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.hll_sc_app.base.R;

/**
 * 带星号的 TextView
 *
 * @author zhuyingsong
 * @date 2019-06-17
 */
public class StartTextView extends AppCompatTextView {
    public StartTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (TextUtils.isEmpty(getText())) {
            return;
        }
        SpannableString spannableString = new SpannableString(getText() + "*");
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.base_red)),
            spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableString);
    }

    public StartTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StartTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
