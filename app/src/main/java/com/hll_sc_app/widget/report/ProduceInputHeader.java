package com.hll_sc_app.widget.report;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputHeader extends ConstraintLayout {
    @BindView(R.id.pih_date)
    TextView mDate;
    @BindView(R.id.pih_person)
    TextView mPerson;
    @BindView(R.id.pih_shift)
    TextView mShift;

    public ProduceInputHeader(Context context) {
        this(context, null);
    }

    public ProduceInputHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProduceInputHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.WHITE);
        View view = View.inflate(context, R.layout.view_report_produce_input_header, this);
        ButterKnife.bind(this, view);
        Date local = new Date();
        mDate.setText(CalendarUtils.format(local, Constants.SLASH_YYYY_MM_DD));
        mPerson.setText(GreenDaoUtils.getUser().getEmployeeName());
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mShift.setOnClickListener(l);
    }

    public void setShift(CharSequence s) {
        mShift.setText(s);
    }

    public void setShiftColor(int color) {
        mShift.setTextColor(color);
    }
}
