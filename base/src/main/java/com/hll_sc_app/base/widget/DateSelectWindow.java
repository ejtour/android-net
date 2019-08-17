package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 日期区间选择
 *
 * @author 朱英松
 * @date 2017/11/3
 */
public class DateSelectWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private Activity mActivity;

    private int mYear;
    private WheelView mYearPicker;
    private int mMonth;
    private WheelView mMonthPicker;
    private int mDay;
    private WheelView mDayPicker;
    private int startYear;
    private DateSelectListener mSelectListener;
    private TextView mTxtStartDate;
    private TextView mTxtEndDate;


    public DateSelectWindow(Activity context) {
        super(context);
        this.mActivity = context;
        View rootView = View.inflate(context, R.layout.base_window_date_select, null);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb000000));
        startYear = 2010;
        mTxtEndDate = rootView.findViewById(R.id.txt_end_date);
        mTxtStartDate = rootView.findViewById(R.id.txt_start_date);
        mTxtStartDate.setOnClickListener(v -> {
            mTxtStartDate.setSelected(true);
            mTxtEndDate.setSelected(false);
            String[] strings = mTxtStartDate.getText().toString().split("-");
            setSelect(CommonUtils.getInt(strings[0]), CommonUtils.getInt(strings[1]), CommonUtils.getInt(strings[2]));
        });
        mTxtEndDate.setOnClickListener(v -> {
            mTxtStartDate.setSelected(false);
            mTxtEndDate.setSelected(true);
            String[] strings = mTxtEndDate.getText().toString().split("-");
            setSelect(CommonUtils.getInt(strings[0]), CommonUtils.getInt(strings[1]), CommonUtils.getInt(strings[2]));
        });
        initStartPicker(rootView);
    }

    private void initStartPicker(View rootView) {
        Calendar startCalendar = Calendar.getInstance();
        mYear = startCalendar.get(Calendar.YEAR);
        mMonth = startCalendar.get(Calendar.MONTH) + 1;
        mDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        String dateString = CalendarUtils.format(startCalendar.getTime(), "yyyy-MM-dd");
        mTxtStartDate.setText(dateString);
        mTxtEndDate.setText(dateString);
        mTxtEndDate.setSelected(true);
        initYearPicker(rootView);
        initMonthPicker(rootView);
        initDayPicker(rootView);
        rootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);
    }

    private void initYearPicker(View rootView) {
        mYearPicker = rootView.findViewById(R.id.picker_year);
        mYearPicker.setVisibleItems(5);
        mYearPicker.setCyclic(true);
        mYearPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int temp = mYear;
                mYear = mYearPicker.getCurrentItem() + startYear;
                if (checkLegal()) {
                    setDateText();
                } else {
                    mYear = temp;
                    mYearPicker.setCurrentItem(mYear - startYear);
                }
            }
        });
        mYearPicker.setViewAdapter(new YearWheelAdapter(mActivity));
        mYearPicker.setCurrentItem(mYear - startYear);
    }

    private void initMonthPicker(View rootView) {
        mMonthPicker = rootView.findViewById(R.id.picker_month);
        mMonthPicker.setVisibleItems(5);
        mMonthPicker.setCyclic(true);
        mMonthPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int temp = mMonth;
                mMonth = mMonthPicker.getCurrentItem() + 1;
                if (checkLegal()) {
                    mDayPicker.setViewAdapter(new DayWheelAdapter(mActivity));
                    mDayPicker.setCurrentItem(mDay - 1);
                    setDateText();
                } else {
                    mMonth = temp;
                    mMonthPicker.setCurrentItem(mMonth - 1);
                }
            }
        });
        mMonthPicker.setViewAdapter(new MonthWheelAdapter(mActivity));
        mMonthPicker.setCurrentItem(mMonth - 1);
    }

    private void initDayPicker(View rootView) {
        mDayPicker = rootView.findViewById(R.id.picker_day);
        mDayPicker.setVisibleItems(5);
        mDayPicker.setCyclic(true);
        mDayPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int temp = mDay;
                mDay = mDayPicker.getCurrentItem() + 1;
                if (checkLegal()) {
                    setDateText();
                } else {
                    mDay = temp;
                    mDayPicker.setCurrentItem(mDay - 1);
                }
            }
        });
        mDayPicker.setViewAdapter(new DayWheelAdapter(mActivity));
        mDayPicker.setCurrentItem(mDay - 1);
    }

    private void setDateText() {
        String dateString = CalendarUtils.format(CalendarUtils.toCalendar(mYear, mMonth, mDay).getTime(), "yyyy" +
            "-MM-dd");
        if (mTxtStartDate.isSelected()) {
            mTxtStartDate.setText(dateString);
        } else if (mTxtEndDate.isSelected()) {
            mTxtEndDate.setText(dateString);
        }
    }

    private boolean checkLegal() {
        boolean isLegal = false;
        if (mTxtStartDate.isSelected()) {
            isLegal = checkStartLegal();
        } else if (mTxtEndDate.isSelected()) {
            isLegal = checkEndLegal();
        }
        return isLegal;
    }

    private boolean checkStartLegal() {
        boolean isLegal = false;
        Date selectDate =
            CalendarUtils.parse(CalendarUtils.format(CalendarUtils.toCalendar(mYear, mMonth, mDay).getTime(),
                CalendarUtils.FORMAT_SERVER_DATE), CalendarUtils.FORMAT_SERVER_DATE);
        Date currentDate = CalendarUtils.parse(CalendarUtils.format(new Date(), CalendarUtils.FORMAT_SERVER_DATE),
            CalendarUtils.FORMAT_SERVER_DATE);
        Date endDate = CalendarUtils.parse(mTxtEndDate.getText().toString().replace("-", ""),
            CalendarUtils.FORMAT_SERVER_DATE);
        if (selectDate != null && endDate != null) {
            // 起始时间大于当前时间小于结束时间
            isLegal = !selectDate.before(currentDate) && !selectDate.after(endDate);
        }
        return isLegal;
    }

    private boolean checkEndLegal() {
        boolean isLegal = false;
        Date selectDate =
            CalendarUtils.parse(CalendarUtils.format(CalendarUtils.toCalendar(mYear, mMonth, mDay).getTime(),
                CalendarUtils.FORMAT_SERVER_DATE), CalendarUtils.FORMAT_SERVER_DATE);
        Date startDate = CalendarUtils.parse(mTxtStartDate.getText().toString().replace("-", ""),
            CalendarUtils.FORMAT_SERVER_DATE);
        if (selectDate != null && startDate != null) {
            isLegal = !selectDate.before(startDate);
        }
        return isLegal;
    }

    public void setSelectListener(DateSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    /**
     * 设置选中的日期
     *
     * @param year  月
     * @param month 月
     * @param day   日
     */
    public void setSelect(int year, int month, int day) {
        mYear = year;
        int indexYear = mYear - startYear;
        mYearPicker.setCurrentItem(indexYear < 0 ? 0 : indexYear);

        mMonth = month;
        int indexMonth = month - 1;
        mMonthPicker.setCurrentItem(indexMonth < 0 ? 0 : indexMonth);

        mDay = day;
        int indexDay = day - 1;
        mDayPicker.setCurrentItem(indexDay < 0 ? 0 : indexDay);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_confirm) {
            if (mSelectListener != null) {
                String startDate = mTxtStartDate.getText().toString().replace("-", "");
                String endDate = mTxtEndDate.getText().toString().replace("-", "");
                mSelectListener.select(startDate, endDate);
            }
        }
        dismiss();
    }

    /**
     * 点击确定后监听
     */
    public interface DateSelectListener {
        /**
         * 选择日期区间
         *
         * @param startDate 开始时间
         * @param endDate   结束时间
         */
        void select(String startDate, String endDate);
    }

    class MonthWheelAdapter extends AbstractWheelTextAdapter {
        MonthWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return (index + 1) + "月";
        }

        @Override
        public int getItemsCount() {
            return 12;
        }
    }

    class YearWheelAdapter extends AbstractWheelTextAdapter {
        YearWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return startYear + index + "年";
        }

        @Override
        public int getItemsCount() {
            return 20;
        }
    }

    class DayWheelAdapter extends AbstractWheelTextAdapter {
        DayWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return (index + 1) + "日";
        }

        @Override
        public int getItemsCount() {
            return CalendarUtils.getEndDay(mYear, mMonth);
        }
    }
}
