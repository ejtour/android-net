package com.hll_sc_app.widget.cooperation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/10/20.
 */
public class DeliveryAgingTipsView extends LinearLayout {
    public DeliveryAgingTipsView(Context context) {
        this(context, null);
    }

    public DeliveryAgingTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeliveryAgingTipsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        addView(getChildView(ContextCompat.getColor(context, R.color.color_aeaeae), "更多到货时间请在"));
        TextView btn = getChildView(ContextCompat.getColor(context, R.color.colorPrimary), "配送时效");
        btn.setOnClickListener(v -> RouterUtil.goToActivity(RouterConfig.DELIVERY_AGEING_MANAGE));
        addView(btn);
        addView(getChildView(ContextCompat.getColor(context, R.color.color_aeaeae), "中设置"));
        if (UserConfig.crm()) {
            setVisibility(GONE);
        }
    }

    private TextView getChildView(int color, String text) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(12);
        textView.setTextColor(color);
        textView.setText(text);
        return textView;
    }
}
