package com.hll_sc_app.widget.report;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private Date mDateObj;
    private DateWindow mDateWindow;

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

    public void setDate(String date) {
        setDateSelectable(date == null);
        mDateObj = date == null ? new Date() : DateUtil.parse(date);
        mDate.setText(CalendarUtils.format(mDateObj, Constants.SLASH_YYYY_MM_DD));
    }

    public String getDateStr() {
        return CalendarUtils.toLocalDate(mDateObj);
    }

    private void setDateSelectable(boolean selectable) {
        mDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, selectable ? R.drawable.ic_arrow_gray : 0, 0);
        mDate.setClickable(selectable);
    }

    @OnClick(R.id.pih_date)
    void selectDate(View view) {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow((Activity) getContext());
            mDateWindow.setCalendar(mDateObj);
            mDateWindow.setSelectListener(date -> {
                mDateObj = date;
                mDate.setText(CalendarUtils.format(mDateObj, Constants.SLASH_YYYY_MM_DD));
            });
        }
        mDateWindow.showAtLocation(view, Gravity.END, 0, 0);
    }
}
