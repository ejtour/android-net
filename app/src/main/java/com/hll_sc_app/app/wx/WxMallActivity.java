package com.hll_sc_app.app.wx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/12
 */

@Route(path = RouterConfig.WX_MALL)
public class WxMallActivity extends BaseActivity {
    @BindView(R.id.awm_step_3)
    TextView mStep3;
    @BindView(R.id.awm_step_4)
    TextView mStep4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_mall);
        ButterKnife.bind(this);
        String source = mStep3.getText().toString();
        SpannableString spannableString = new SpannableString(source);
        int color = ContextCompat.getColor(this, R.color.color_222222);
        spannableString.setSpan(new ForegroundColorSpan(color), source.indexOf("“") + 1, source.indexOf("”"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStep3.setText(spannableString);
        source = mStep4.getText().toString();
        spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(color), source.indexOf("管"), source.indexOf("扫"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStep4.setText(spannableString);
    }
}
