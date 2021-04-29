package com.hll_sc_app.widget.print;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.print.PrintTemplateBean;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
public class PrintTemplateActionBar extends LinearLayout implements View.OnClickListener {

    private OnClickListener mListener;

    public PrintTemplateActionBar(Context context) {
        this(context, null);
    }

    public PrintTemplateActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrintTemplateActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setData(PrintTemplateBean bean) {
        removeAllViews();
        if (bean.isHasActive() == null) {
            addView(createAction(getContext(), "加到我的", R.id.ipt_add_list, true));
            addView(createAction(getContext(), "预览", R.id.ipt_preview, false), 0);
        } else {
            if (bean.isHasActive()) {
                addView(createAction(getContext(), "预览", R.id.ipt_preview, true));
            } else {
                addView(createAction(getContext(), "启用", R.id.ipt_enable, true));
                addView(createAction(getContext(), "删除", R.id.ipt_delete, false), 0);
                addView(createAction(getContext(), "预览", R.id.ipt_preview, false), 0);
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    private TextView createAction(Context context, String text, int id, boolean primary) {
        TextView textView = new TextView(context);
        textView.setId(id);
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(context, primary ? R.color.colorPrimary : R.color.color_333333));
        textView.setBackgroundResource(primary ? R.drawable.bg_button_mid_stroke_primary : R.drawable.bg_button_mid_stroke_gray);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(78), ViewGroup.LayoutParams.MATCH_PARENT);
        if (!primary) {
            params.rightMargin = UIUtils.dip2px(10);
        }
        textView.setLayoutParams(params);
        textView.setOnClickListener(this);
        return textView;
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v);
    }
}
