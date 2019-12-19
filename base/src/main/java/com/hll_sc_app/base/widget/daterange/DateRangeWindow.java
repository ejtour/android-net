package com.hll_sc_app.base.widget.daterange;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.citymall.util.ToastUtils;

import java.util.Date;
import java.util.Locale;

/**
 * 日期区间选择
 *
 * @author zhuyingsong
 * @date 2019/2/13
 */
public class DateRangeWindow extends BasePopupWindow implements View.OnClickListener, CalendarView.OnCalendarRangeSelectListener {
    private Calendar mSelectedStart, mSelectedEnd;
    private Calendar mPreStart, mPreEnd;
    private TextView mTxtDate, mBtnReset;
    private CalendarView mRangeCalendarView;
    private OnRangeSelectListener mSelectListener;
    private OnRangeChangedListener mListener;

    public DateRangeWindow(Activity activity) {
        super(activity);
        View rootView = View.inflate(mActivity, R.layout.base_window_date_range, null);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        rootView.setOnClickListener(this);
        mBtnReset = rootView.findViewById(R.id.txt_reset);
        mBtnReset.setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);
        rootView.findViewById(R.id.img_left).setOnClickListener(this);
        rootView.findViewById(R.id.img_right).setOnClickListener(this);
        mRangeCalendarView = rootView.findViewById(R.id.calendarView);
        mRangeCalendarView.setOnCalendarRangeSelectListener(this);
        mTxtDate = rootView.findViewById(R.id.txt_date);
        mRangeCalendarView.setOnMonthChangeListener((year, month) -> {
            mTxtDate.setText(String.format(Locale.getDefault(), "%d年%d月", year, month));
        });
        mRangeCalendarView.scrollToCurrent();
    }

    /**
     * 设置选中监听
     *
     * @param onSingleSelectListener 监听
     */
    public void setOnRangeSelectListener(OnRangeSelectListener onSingleSelectListener) {
        this.mSelectListener = onSingleSelectListener;
    }

    public void setOnRangeChangedListener(OnRangeChangedListener listener) {
        mListener = listener;
    }

    public void setReset(boolean canReset) {
        mBtnReset.setVisibility(canReset ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_reset) {
            mSelectedStart = null;
            mSelectedEnd = null;
            mRangeCalendarView.clearSelectRange();
        } else if (v.getId() == R.id.txt_confirm) {
            confirm();
            if (mSelectListener != null) {
                if (mSelectedStart == null && mSelectedEnd == null) {
                    mSelectListener.onSelected(null, null);
                    dismiss();
                    return;
                }
                if (mSelectedStart == null) {
                    ToastUtils.showShort(v.getContext(), "请选择起始时间");
                } else if (mSelectedEnd == null) {
                    ToastUtils.showShort(v.getContext(), "请选择结束时间");
                } else {
                    mSelectListener.onSelected(mSelectedStart, mSelectedEnd);
                    dismiss();
                }
            }
        } else if (v.getId() == R.id.img_left) {
            mRangeCalendarView.scrollToPre(true);
        } else if (v.getId() == R.id.img_right) {
            mRangeCalendarView.scrollToNext(true);
        } else {
            dismiss();
        }
    }

    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {
        // no-op
    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        // no-op
    }

    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
        if (!isEnd) {
            mSelectedStart = calendar;
            mSelectedEnd = calendar;
        } else {
            mSelectedEnd = calendar;
        }
    }

    /**
     * 设置默认选中的范围
     *
     * @param startYear
     * @param startMonth
     * @param startDay
     * @param endYear
     * @param endMonth
     * @param endDay
     */
    public void setSelectCalendarRange(int startYear, int startMonth, int startDay,
                                       int endYear, int endMonth, int endDay) {
        mRangeCalendarView.setSelectCalendarRange(startYear, startMonth, startDay,
                endYear, endMonth, endDay);
    }

    /**
     * 设置默认选中的范围
     */
    public void setSelectCalendarRange(Date startDate, Date endDate) {
        java.util.Calendar start = java.util.Calendar.getInstance(), end = java.util.Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        mPreStart = new Calendar();
        mPreStart.setYear(start.get(java.util.Calendar.YEAR));
        mPreStart.setMonth(start.get(java.util.Calendar.MONTH) + 1);
        mPreStart.setDay(start.get(java.util.Calendar.DATE));
        mPreEnd = new Calendar();
        mPreEnd.setYear(end.get(java.util.Calendar.YEAR));
        mPreEnd.setMonth(end.get(java.util.Calendar.MONTH) + 1);
        mPreEnd.setDay(end.get(java.util.Calendar.DATE));
        mRangeCalendarView.setSelectCalendarRange(mPreStart, mPreEnd);
    }

    private void confirm() {
        if (mListener != null) {
            dismiss();
            boolean hasChanged = false;
            if (mPreStart != null) {
                hasChanged = !mPreStart.equals(mSelectedStart);
            }
            if (!hasChanged && mPreEnd != null) {
                hasChanged = !mPreEnd.equals(mSelectedEnd);
            }
            if (!hasChanged && (mPreStart == null || mPreEnd == null)) {
                hasChanged = mSelectedStart != null && mSelectedEnd != null;
            }
            if (hasChanged) {
                mPreStart = mSelectedStart;
                mPreEnd = mSelectedEnd;
                Date startDate = null, endDate = null;
                if (mSelectedStart != null) {
                    startDate = new Date(mSelectedStart.getTimeInMillis());
                }
                if (mSelectedEnd != null) {
                    endDate = new Date(mSelectedEnd.getTimeInMillis());
                }
                mListener.onRangeChanged(startDate, endDate);
            }
        }
    }

    /**
     * 设置默认选中的范围
     *
     * @param startCalendar
     * @param endCalendar
     */
    public void setSelectCalendarRange(Calendar startCalendar, Calendar endCalendar) {
        mRangeCalendarView.setSelectCalendarRange(startCalendar, endCalendar);

    }

    public interface OnRangeSelectListener {
        /**
         * 选中监听
         *
         * @param calendarStart 选中的起始时间
         * @param calendarEnd   选中的结束时间
         */
        void onSelected(Calendar calendarStart, Calendar calendarEnd);
    }

    public interface OnRangeChangedListener {
        void onRangeChanged(Date start, Date end);
    }
}
