package com.hll_sc_app.widget.report;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportTipsView extends LinearLayout {
    @BindView(R.id.vrt_image)
    ImageView mImage;
    @BindView(R.id.vrt_text)
    TextView mText;

    public ReportTipsView(Context context) {
        this(context, null);
    }

    public ReportTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReportTipsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        View view = View.inflate(context, R.layout.view_report_tips, this);
        ButterKnife.bind(this, view);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ReportTipsView);
        int color = array.getResourceId(R.styleable.ReportTipsView_rtv_tintRes, R.color.color_999999);
        ImageViewCompat.setImageTintList(mImage, ContextCompat.getColorStateList(context, color));
        String string = array.getString(R.styleable.ReportTipsView_rtv_tips);
        if (!TextUtils.isEmpty(string)) mText.setText(string);
        mText.setTextColor(ContextCompat.getColor(context, color));
        array.recycle();
    }

    public void setTips(String tips) {
        mText.setText(tips);
    }
}
