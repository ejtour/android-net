package com.hll_sc_app.widget.report;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputFooter extends ConstraintLayout {
    @BindView(R.id.pif_add)
    TextView mAdd;
    @BindView(R.id.pif_total)
    TextView mTotal;

    public ProduceInputFooter(Context context) {
        this(context, null);
    }

    public ProduceInputFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProduceInputFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.WHITE);
        View view = View.inflate(context, R.layout.view_report_produce_input_footer, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mAdd.setOnClickListener(l);
    }

    public void setAmount(CharSequence s) {
        mTotal.setText(s);
    }
}
