package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 日期选择只选择年份
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
        startYear = 2000;
        initStartPicker(rootView);
    }

    private void initStartPicker(View rootView) {
        Calendar startCalendar = Calendar.getInstance();
        mYear = startCalendar.get(Calendar.YEAR);
        mMonth = startCalendar.get(Calendar.MONTH) + 1;
        mDay = startCalendar.get(Calendar.DAY_OF_MONTH);
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
                mYear = mYearPicker.getCurrentItem() + startYear;
            }
        });
        mYearPicker.setViewAdapter(new YearWheelAdapter(mActivity));
    }

    private void initMonthPicker(View rootView) {
        mMonthPicker = rootView.findViewById(R.id.picker_month);
        mMonthPicker.setVisibleItems(5);
        mMonthPicker.setCyclic(true);
        mMonthPicker.addChangingListener((wheel, oldValue, newValue) -> {
            mMonth = newValue;
            int maxDay = CalendarUtils.getEndDay(mYear, mMonth);
            if (mDay > maxDay) {
                mDay = maxDay;
            }
        });
        mMonthPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mMonthPicker.setViewAdapter(new DayWheelAdapter(mActivity));
            }
        });
        mMonthPicker.setViewAdapter(new MonthWheelAdapter(mActivity));
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
                mDay = mDayPicker.getCurrentItem() + 1;
            }
        });
        mDayPicker.setViewAdapter(new DayWheelAdapter(mActivity));
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
        int indexYear = mYear - startYear - 1;
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
//                mSelectListener.select(mSelectDay, mSelectTime);
            }
        }
        dismiss();
    }

    /**
     * 点击确定后监听
     */
    public interface DateSelectListener {
        /**
         * 选择的配送日期、时间
         *
         * @param day  日期
         * @param time 时间
         */
        void select(String day, String time);
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
            return 10;
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
