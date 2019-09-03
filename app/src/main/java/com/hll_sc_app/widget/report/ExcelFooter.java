package com.hll_sc_app.widget.report;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public class ExcelFooter extends ExcelRow {
    public ExcelFooter(Context context) {
        this(context, null);
    }

    public ExcelFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExcelFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void initView() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.bg_excel_footer);
        }
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected int getDivHeight() {
        return UIUtils.dip2px(12);
    }
}
